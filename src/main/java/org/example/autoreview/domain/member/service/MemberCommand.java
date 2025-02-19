package org.example.autoreview.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberCommand {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomRuntimeException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }
}
