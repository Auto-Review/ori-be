package org.example.autoreview.domain.post.TIL.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.post.TIL.dto.request.TILPostRequestDto;
import org.example.autoreview.domain.post.TIL.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.post.TIL.entity.TILPost;
import org.example.autoreview.domain.post.TIL.entity.TILPostRepository;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TILPostService {

    private final TILPostRepository tilPostRepository;

    public List<TILPostResponseDto> findAll(){
        return tilPostRepository.findAll().stream()
                .map(TILPostResponseDto::new)
                .collect(Collectors.toList());
    }

    public TILPostResponseDto findById(Long id){
        TILPost tilPost = tilPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.POST_NOT_FOUND)
        );
        return new TILPostResponseDto(tilPost);
    }

    public Long update(TILPostRequestDto requestDto) {
        Long id = requestDto.getId();

        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        tilPost.update(requestDto);

        return id;
    }

    public void delete(Long id){
        TILPost tilPost = tilPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        tilPostRepository.delete(tilPost);
    }
}
