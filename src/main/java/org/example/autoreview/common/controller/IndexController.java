package org.example.autoreview.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.member.dto.MemberResponseDto;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.member.sociallogin.TokenPrefix;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCode;
import org.example.autoreview.domain.member.sociallogin.oauth2.randomcode.RandomCodeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final RandomCodeRepository randomCodeRepository;

    @GetMapping("/")
    public String index(@RequestParam(name = "randomCode", required = false) String randomCode
            , Model model){

        if(randomCode != null){
            log.info("start randomcode");
            // test용 나중에 오류 코드 따로 생성할 것
            RandomCode code = randomCodeRepository.findById(TokenPrefix.RANDOMCODE.toString() + randomCode)
                    .orElseThrow(IllegalArgumentException::new);

            log.info(code.getEmail());
            model.addAttribute("email", code.getEmail());
        }

        return "index";
    }
}
