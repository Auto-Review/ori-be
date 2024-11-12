package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.CodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.BadRequestException;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CodePostService {

    private final CodePostRepository codePostRepository;

    @Transactional
    public Long save(CodePostSaveRequestDto requestDto, Member member) {
        CodePost codePost = requestDto.toEntity(member);
        return codePostRepository.save(codePost).getId();
    }

    public CodePost findEntityById(Long id) {
        return codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
    }

    public CodePostResponseDto findById(Long id) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );

        return new CodePostResponseDto(codePost);
    }

    public CodePostListResponseDto search(String keyword, Pageable pageable) {
        keywordValidator(keyword);
        Page<CodePost> codePostPage = codePostRepository.search(keyword, pageable);

        return new CodePostListResponseDto(convertListDto(codePostPage), codePostPage.getTotalPages());
    }

    public CodePostListResponseDto mySearch(String keyword, Pageable pageable, Member member) {
        keywordValidator(keyword);
        Page<CodePost> codePostPage = codePostRepository.mySearch(keyword, pageable, member.getId());
        return new CodePostListResponseDto(convertListDto(codePostPage), codePostPage.getTotalPages());
    }

    private static void keywordValidator(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PARAMETER.getMessage());
        }
    }

    public CodePostListResponseDto findByMemberId(Pageable pageable, Member member) {
        Page<CodePost> codePostPage = codePostRepository.findByMemberId(member.getId(),pageable);
        return new CodePostListResponseDto(convertListDto(codePostPage), codePostPage.getTotalPages());
    }

    public CodePostListResponseDto findByPage(Pageable pageable) {
        Page<CodePost> page = codePostRepository.findByPage(pageable);
        List<CodePostThumbnailResponseDto> dtoList = convertListDto(page);

        return new CodePostListResponseDto(dtoList, page.getTotalPages());
    }

    private List<CodePostThumbnailResponseDto> convertListDto(Page<CodePost> page) {
        return page.stream()
                .map(this::getCodePostThumbnailResponseDto)
                .collect(Collectors.toList());
    }

    private CodePostThumbnailResponseDto getCodePostThumbnailResponseDto(CodePost codePost) {
        return new CodePostThumbnailResponseDto(codePost, summary(codePost.getDescription()));
    }

    //TODO: 지금은 너무 비효율 적으로 보여서 나중에 수정 해야함
    private String summary(String description) {
        if (description.length() > 200) {
            return description.substring(0,200);
        }
        return description;
    }

    @Transactional
    public Long update(CodePostUpdateRequestDto requestDto, String email) {
        Long id = requestDto.getId();
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        userValidator(email, codePost);
        codePost.update(requestDto);
        return id;
    }

    @Transactional
    public Long delete(Long id, String email) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        userValidator(email, codePost);
        codePostRepository.delete(codePost);
        return id;
    }

    private static void userValidator(String email, CodePost codePost) {
        if (!codePost.getMember().getEmail().equals(email)) {
            throw new BadRequestException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
