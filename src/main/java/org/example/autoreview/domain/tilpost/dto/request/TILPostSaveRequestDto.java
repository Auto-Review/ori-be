package org.example.autoreview.domain.tilpost.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.tilpost.entity.TILPost;

@Getter
@Builder
public class TILPostSaveRequestDto {

    private String title;
    private String content;

    public TILPost toEntity(Member entity){
        return TILPost.builder()
                .writerId(entity.getId())
                .writerEmail(entity.getEmail())
                .writerNickName(entity.getNickname())
                .title(title)
                .content(content)
                .build();
    }
}
