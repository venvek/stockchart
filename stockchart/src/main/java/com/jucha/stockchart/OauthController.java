package com.jucha.stockchart;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

	@GetMapping("/")
    public String home() {
        return "메인 페이지 (로그인 버튼 만들어서 /oauth2/authorization/google 로 이동)";
    }

    @GetMapping("/google")
    public String profile(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return "로그인한 사용자: " + oAuth2User.getAttributes();
    }
    
    
    @GetMapping("/naver")
    public String profile2(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return "로그인한 사용자: " + oAuth2User.getAttributes();
    }
    
    @GetMapping("/kakao")
    public String profile3(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return "로그인한 사용자: " + oAuth2User.getAttributes();
    }
}
