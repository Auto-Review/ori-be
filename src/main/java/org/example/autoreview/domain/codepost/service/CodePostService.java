package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
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
    public Long save(CodePostSaveRequestDto requestDto){
        CodePost codePost = requestDto.toEntity();

        return codePostRepository.save(codePost).getId();
    }

    public CodePost findEntityById(Long id){
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


    public CodePostListResponseDto findByPage(Pageable pageable) {
        List<CodePostResponseDto> dtoList = findAll(pageable);
        return new CodePostListResponseDto(dtoList, pageable.getPageSize());
    }

    private List<CodePostResponseDto> findAll(Pageable pageable){
        return codePostRepository.findByPage(pageable).stream()
                .map(CodePostResponseDto::new)
                .collect(Collectors.toList());
    }



    public Long update(CodePostUpdateRequestDto requestDto) {
        Long id = requestDto.getId();
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        codePost.update(requestDto);
        return id;
    }

    public Long delete(Long id) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_POST)
        );
        codePostRepository.delete(codePost);
        return id;
    }

}
