package org.example.autoreview.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberUpdateDto;
import org.example.autoreview.domain.member.service.MyPageService;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/v1/api/profile")
@RequiredArgsConstructor
@RestController
public class MemberPageController {

    private final MyPageService myPageService;

    @GetMapping("/info")
    public ResponseEntity<MemberResponseDto> info(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(myPageService.memberInfo(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MemberUpdateDto requestDto) {
        return ResponseEntity.ok().body(myPageService.memberUpdate(requestDto));
    }

    @GetMapping("/bookmark/til")
    public ResponseEntity<TILPageResponseDto> bookmarkedTIL(@PageableDefault(page = 0, size = 9) Pageable pageable,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(myPageService.memberBookmarkedTILPost(userDetails.getUsername(), pageable));
    }
}
