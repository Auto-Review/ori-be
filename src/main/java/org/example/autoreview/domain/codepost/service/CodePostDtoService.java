package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.dto.response.MyCodePostThumbnailResponseDto;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.notification.enums.NotificationStatus;
import org.example.autoreview.domain.notification.service.NotificationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodePostDtoService {

    private final CodePostService codePostService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    public Long postSave(CodePostSaveRequestDto requestDto, String email){
        Member member = memberService.findByEmail(email);
        CodePost codePost = codePostService.save(requestDto, member);

        if (requestDto.reviewDay() != null) {
            notificationService.save(member, codePost);
        }

        return codePost.getId();
    }

    public CodePostResponseDto findPostById(Long id, String email){
        return codePostService.findById(id,email);
    }

    public CodePostListResponseDto<CodePostThumbnailResponseDto> postSearch(String keyword, Pageable pageable){
        return codePostService.search(keyword, pageable);
    }

    public CodePostListResponseDto<MyCodePostThumbnailResponseDto> postMySearch(String keyword, Pageable pageable, String email){
        Member member = memberService.findByEmail(email);
        return codePostService.mySearch(keyword, pageable, member);
    }

    public CodePostListResponseDto<MyCodePostThumbnailResponseDto> findPostByMemberId(Pageable pageable, String email){
        Member member = memberService.findByEmail(email);
        return codePostService.findByMemberId(pageable, member);
    }

    public CodePostListResponseDto<CodePostThumbnailResponseDto> findPostByPage(Pageable pageable){
        return codePostService.findByPage(pageable);
    }

    public Long postUpdate(CodePostUpdateRequestDto requestDto, String email) {
        CodePost codePost = codePostService.update(requestDto, email);
        boolean notificationExists = notificationService.existsByCodePostId(requestDto.getId());

        if (notificationExists) {
            if (requestDto.getReviewDay() == null) {
                notificationService.delete(email, codePost.getId());
            } else {
                notificationService.update(email, codePost, NotificationStatus.PENDING);
            }
        } else {
            Member member = memberService.findByEmail(email);
            notificationService.save(member, codePost);
        }

        return codePost.getId();
    }

    public Long postDelete(Long id, String email){
        if (notificationService.existsByCodePostId(id)) {
            notificationService.delete(email,id);
        }
        return codePostService.delete(id, email);
    }
}
