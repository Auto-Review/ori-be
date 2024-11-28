package org.example.autoreview.domain.codepost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
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
        return codePostService.save(requestDto, member);
    }

    public CodePostResponseDto findPostById(Long id){
        return codePostService.findById(id);
    }

    public CodePostListResponseDto postSearch(String keyword, Pageable pageable){
        return codePostService.search(keyword, pageable);
    }

    public CodePostListResponseDto postMySearch(String keyword, Pageable pageable, String email){
        Member member = memberService.findByEmail(email);
        return codePostService.mySearch(keyword, pageable, member);
    }

    public CodePostListResponseDto findPostByMemberId(Pageable pageable, String email){
        Member member = memberService.findByEmail(email);
        return codePostService.findByMemberId(pageable, member);
    }

    public CodePostListResponseDto findPostByPage(Pageable pageable){
        return codePostService.findByPage(pageable);
    }

    public Long postUpdate(CodePostUpdateRequestDto requestDto, String email){
        return codePostService.update(requestDto, email);
    }

    public Long postDelete(Long id, String email){
        if (notificationService.existsById(id)) {
            notificationService.delete(email, id);
        }
        return codePostService.delete(id, email);
    }
}
