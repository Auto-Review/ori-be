package org.example.autoreview.domain.tilpost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.global.common.basetime.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class TILPost extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "til_post_id")
    private Long id;

    @Column(nullable = false)
    private Long writerId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 3000, nullable = false)
    private String content;

    @Builder
    public TILPost(String title, String content, Long writerId) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
    }

    public void update(TILPostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
