package org.example.autoreview.domain.tilpost.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TILPostUpdateRequestDto {

    private Long id;

    private String writerEmail;

    private String writerNickName;

    private String title;

    private String content;

    @Builder
    public TILPostUpdateRequestDto(Long id, String writerEmail, String writerNickName, String title, String content) {
        this.id = id;
        this.writerEmail = writerEmail;
        this.writerNickName = writerNickName;
        this.title = title;
        this.content = content;
    }
}
