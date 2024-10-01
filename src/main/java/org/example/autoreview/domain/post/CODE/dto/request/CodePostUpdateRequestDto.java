package org.example.autoreview.domain.post.CODE.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.post.CODE.entity.CodePost;

import java.time.LocalDateTime;

@Getter
@Builder
public class CodePostUpdateRequestDto {

    private Long id;

    private String title;

    private String content;

    private int level;

    private LocalDateTime reviewTime;

    private String code;
}
