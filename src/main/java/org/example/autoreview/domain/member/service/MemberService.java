package org.example.autoreview.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.common.service.TokenVerifierService;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberSaveDto;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveOrFind(String email){
        return memberRepository.findByEmail(email).orElse(
                memberRepository.save(new MemberSaveDto(email, "user").toEntity()));
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
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return new MemberResponseDto(entity);
    }


    @Transactional
    public Long update(Long id, String nickname){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        entity.update(nickname);

        return id;
    }

    @Transactional
    public void delete(Long id){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        memberRepository.delete(entity);
    }
}
