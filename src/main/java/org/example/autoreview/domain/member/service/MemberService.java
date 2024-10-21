package org.example.autoreview.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.common.service.TokenVerifierService;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.dto.MemberSaveDto;
import org.example.autoreview.domain.member.entity.MemberRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtDto;
import org.example.autoreview.domain.member.sociallogin.jwt.JwtProvider;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.RefreshToken;
import org.example.autoreview.domain.member.sociallogin.jwt.refresh.RefreshTokenRepository;
import org.example.autoreview.exception.errorcode.ErrorCode;
import org.example.autoreview.exception.sub_exceptions.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final TokenVerifierService tokenVerifierService;
    // TODO 나중에 service로 변경하기
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long save(MemberSaveDto saveDto){
        return memberRepository.save(saveDto.toEntity()).getId();
    }


    @Transactional
    public JwtDto issuedToken(String accessToken) throws JsonProcessingException {

        Map<String, Object> payload = tokenVerifierService.validateGoogleAccessToken(accessToken);

        String email = (String) payload.get("email");

        Member member = memberRepository.findByEmail(email).orElse(
                memberRepository.save(new MemberSaveDto(email, "user").toEntity()));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), "");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        JwtDto jwtDto = jwtProvider.generateToken(authentication);

        RefreshToken redis = new RefreshToken(jwtDto.getRefreshToken(), email);
        refreshTokenRepository.save(redis);

        return jwtDto;
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
