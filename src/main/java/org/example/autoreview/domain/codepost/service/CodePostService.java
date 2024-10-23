package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
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

    public List<CodePostResponseDto> findAll(){
        return codePostRepository.findAll().stream()
                .map(CodePostResponseDto::new)
                .collect(Collectors.toList());
    }

    public CodePostResponseDto findById(Long id) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.POST_NOT_FOUND)
        );

        return new CodePostResponseDto(codePost);
    }

    public Long update(CodePostUpdateRequestDto requestDto) {
        Long id = requestDto.getId();
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.POST_NOT_FOUND)
        );
        codePost.update(requestDto);
        return id;
    }

    public Long delete(Long id) {
        CodePost codePost = codePostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.POST_NOT_FOUND)
        );
        codePostRepository.delete(codePost);
        return id;
    }

}
