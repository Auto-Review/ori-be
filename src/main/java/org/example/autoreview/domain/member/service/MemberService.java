package org.example.autoreview.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberSaveDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveOrFind(String email){
        return memberRepository.findByEmail(email).orElseGet(() ->
                memberRepository.save(new MemberSaveDto(email).toEntity()));
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll(){
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        return new MemberResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Member findEntityById(Long id){
        return memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Transactional
    public Long update(Long id, String nickname){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        entity.update(nickname);

        return id;
    }

    @Transactional
    public void delete(Long id){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        memberRepository.delete(entity);
    }
}
