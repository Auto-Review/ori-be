package org.example.autoreview.domain.post.TIL.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.autoreview.common.basetime.BaseEntity;
import org.example.autoreview.domain.post.TIL.dto.request.TILPostRequestDto;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class TILPost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "til_post_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String content;

    // 사진

    @Builder
    public TILPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(TILPostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
