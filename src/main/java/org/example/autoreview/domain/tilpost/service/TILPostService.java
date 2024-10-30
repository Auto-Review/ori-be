package org.example.autoreview.domain.tilpost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostListResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.example.autoreview.domain.tilpost.entity.TILPostRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
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

    private final MemberService memberService;

    private final TILPostRepository tilPostRepository;

    @Transactional
    public Long save(TILPostSaveRequestDto requestDto, String email) {
        Member member = memberService.findByEmail(email);
        TILPost tilPost = requestDto.toEntity(member);
        return tilPostRepository.save(tilPost).getId();
    }

    @Transactional(readOnly = true)
    public List<TILPostListResponseDto> findAll(Pageable pageable){

        return tilPostRepository.findAll(pageable).stream()
                .map(TILPostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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
}
