package org.example.autoreview.domain.tilpost.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.example.autoreview.domain.tilpost.entity.TILPostRepository;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TilPostCommand {

    private final TILPostRepository tilPostRepository;

    public TILPost findById(Long id) {
        return tilPostRepository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_POST)
        );
    }
}
