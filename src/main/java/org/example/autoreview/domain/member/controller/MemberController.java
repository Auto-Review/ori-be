package org.example.autoreview.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberSaveDto;
import org.example.autoreview.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/api/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMembers() {
        return ResponseEntity.ok().body(memberService.findAll());
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> postMember(@RequestBody MemberSaveDto dto){
        return ResponseEntity.ok().body(new MemberResponseDto(memberService.saveOrFind(dto.getEmail())));
    }
}
