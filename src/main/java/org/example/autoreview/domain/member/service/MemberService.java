package org.example.autoreview.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberSaveDto;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveDto saveDto){
        return memberRepository.save(saveDto.toEntity()).getId();
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
                new IllegalArgumentException("해당 회원은 존재하지 않습니다. id=" + id));

        return new MemberResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, String nickname){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원은 존재하지 않습니다. id=" + id));

        entity.update(nickname);

        return id;
    }

    @Transactional
    public void delete(Long id){
        Member entity = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원은 존재하지 않습니다. id=" + id));

        memberRepository.delete(entity);
    }
}
