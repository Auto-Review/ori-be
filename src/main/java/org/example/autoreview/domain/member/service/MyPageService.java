package org.example.autoreview.domain.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.TILBookmark.service.TILBookmarkService;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberUpdateDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.service.TILPostService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MemberService memberService;
    private final TILBookmarkService tilBookmarkService;
    private final TILPostService tilPostService;

    public MemberResponseDto memberInfo(String email){
        return new MemberResponseDto(memberService.findByEmail(email));
    }

    public Long memberUpdate(MemberUpdateDto requestDto){
        return memberService.update(requestDto.getId(), requestDto.getNickname());
    }

    public TILPageResponseDto memberBookmarkedTILPost(String email, Pageable pageable){
        List<Long> postList = tilBookmarkService.findPostIdByMemberEmail(email, pageable);
        return tilPostService.findByIdList(postList, pageable);
    }
}
