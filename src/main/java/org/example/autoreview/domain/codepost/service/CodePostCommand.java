package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.entity.CodePostRepository;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CodePostCommand {

    private final CodePostRepository codePostRepository;

    public CodePost findById(Long id) {
        return codePostRepository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_POST)
        );
    }
}
