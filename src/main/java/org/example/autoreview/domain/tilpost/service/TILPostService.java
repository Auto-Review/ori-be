package org.example.autoreview.domain.tilpost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILCursorResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.example.autoreview.domain.tilpost.entity.TILPostRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TILPostService {

    private final TILPostRepository tilPostRepository;

    @Transactional
    public Long save(TILPostSaveRequestDto requestDto, Member member) {
        TILPost tilPost = requestDto.toEntity(member);
        return tilPostRepository.save(tilPost).getId();
    }

    public TILPageResponseDto findByMember(Member member, Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findTILPostsByMemberIdOrderByIdDesc(member.getId(), pageable);
        return new TILPageResponseDto(convertToListDto(posts), posts.getTotalPages());
    }

    public TILPageResponseDto findByMemberTitleContains(Member member, String keyword, Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findTILPostsByMemberIdAndTitleContainingOrderByIdDesc(member.getId(), keyword, pageable);
        return new TILPageResponseDto(convertToListDto(posts), posts.getTotalPages());
    }

    public TILPageResponseDto findAllByPage(Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findAll(pageable);
        return new TILPageResponseDto(convertToListDto(posts), posts.getTotalPages());
    }

    public TILPageResponseDto findByTitleContains(String keyword, Pageable pageable){
        Page<TILPost> posts = tilPostRepository.findByTitleContaining(keyword, pageable);
        return new TILPageResponseDto(convertToListDto(posts), posts.getTotalPages());
    }

    private List<TILPostThumbnailResponseDto> convertToListDto(Page<TILPost> entity){
        return entity.stream().map(this::getTILPostThumbnailResponseDto).collect(Collectors.toList());
    }

    private TILPostThumbnailResponseDto getTILPostThumbnailResponseDto(TILPost entity){
        return new TILPostThumbnailResponseDto(entity, summary(entity.getContent()));
    }

    private String summary(String content){
        if(content.length() > 200) return content.substring(0, 200);
        return content;
    }

    public TILPostResponseDto findById(Long id){
        TILPost tilPost = tilPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        return new TILPostResponseDto(tilPost);
    }

    @Transactional
    public Long update(TILPostUpdateRequestDto requestDto, String email) {
        Long id = requestDto.getId();

        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));

        Member member = tilPost.getMember();
        if(!Objects.equals(member.getEmail(), email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }

        tilPost.update(requestDto);

        return id;
    }

    @Transactional
    public Long delete(Long id, String email){
        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_POST));

        Member member = tilPost.getMember();
        if(!Objects.equals(member.getEmail(), email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }

        tilPostRepository.delete(tilPost);
        return id;
    }

    public TILCursorResponseDto findAllByIdCursorBased(Long cursorId, int pageSize){
        Pageable pageable = PageRequest.of(0, pageSize + 1);

        List<TILPost> posts = findAllByCursorIdCheckExistsCursor(cursorId, pageable);

        boolean hasNext = hasNext(posts.size(), pageSize);

        return new TILCursorResponseDto (
                toSubListIfHasNext(hasNext, pageSize, posts).stream()
                        .map(this::getTILPostThumbnailResponseDto)
                        .collect(Collectors.toList()),
                cursorId, pageSize);
    }

    private List<TILPost> findAllByCursorIdCheckExistsCursor(Long cursorId, Pageable pageable){
        return cursorId == null ? tilPostRepository.findAllByOrderByIdDesc(pageable)
                : tilPostRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
    }

    private boolean hasNext(int postsSize, int pageSize){
        if(postsSize == 0){
            throw new NotFoundException(ErrorCode.NOT_FOUND_POST);
        }

        return postsSize > pageSize;
    }

    private List<TILPost> toSubListIfHasNext(boolean hasNext, int pageSize, List<TILPost> posts){
        return hasNext ? posts.subList(0, pageSize) : posts;
    }
}
