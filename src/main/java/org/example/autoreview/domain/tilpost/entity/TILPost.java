package org.example.autoreview.domain.tilpost.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.common.basetime.BaseEntity;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public TILPost(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(TILPostUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

}
