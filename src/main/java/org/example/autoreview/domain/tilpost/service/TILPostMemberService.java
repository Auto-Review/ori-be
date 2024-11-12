package org.example.autoreview.domain.tilpost.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TILPostMemberService {

    private final TILPostService tilPostService;
    private final MemberService memberService;

    @Transactional
    public Long postSave(TILPostSaveRequestDto requestDto, String email){
        Member member = memberService.findByEmail(email);
        return tilPostService.save(requestDto, member);
    }

    public TILPageResponseDto findPostByMember(String email, Pageable pageable){
        Member member = memberService.findByEmail(email);
        return tilPostService.findByMember(member, pageable);
    }

    public TILPageResponseDto findPostByMemberTitleContains(String email, String keyword, Pageable pageable){
        Member member = memberService.findByEmail(email);
        return tilPostService.findByMemberTitleContains(member, keyword, pageable);
    }

    public TILPageResponseDto findPostAllByPage(Pageable pageable){
        return tilPostService.findAllByPage(pageable);
    }

    public TILPageResponseDto findPostByTitleContains(String keyword, Pageable pageable){
        return tilPostService.findByTitleContains(keyword, pageable);
    }

    public TILPostResponseDto findPostById(Long id){
        return tilPostService.findById(id);
    }

    public Long postUpdate(TILPostUpdateRequestDto requestDto, String email){
        return tilPostService.update(requestDto, email);
    }

    public Long postDelete(Long id, String email){
        return tilPostService.delete(id, email);
    }

}
