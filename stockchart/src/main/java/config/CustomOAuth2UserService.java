package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jucha.stockchart.User;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, naver, kakao
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        // ✅ 네이버 처리
        if ("naver".equals(registrationId)) {
            attributes = (Map<String, Object>) attributes.get("response");
        }

        // ✅ 카카오 처리
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            // ID, 이메일, 이름을 attributes에 맞춰 넣기
            attributes.put("id", attributes.get("id"));
            attributes.put("email", kakaoAccount.get("email"));
            attributes.put("name", profile.get("nickname"));
        }

        // ID 가져오기
        Object oauthIdObj = attributes.get(userNameAttributeName);
        String oauthId = oauthIdObj != null ? oauthIdObj.toString() : null;

        // DB 조회 or 신규 저장
        User user = userRepository.findByProviderAndProviderId(registrationId, oauthId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProvider(registrationId);
                    newUser.setProviderId(oauthId);

                    // 이메일 안전하게 변환
                    Object emailObj = attributes.get("email");
                    newUser.setEmail(emailObj != null ? emailObj.toString() : null);

                    // 이름 안전하게 변환
                    Object nameObj = attributes.get("name");
                    newUser.setName(nameObj != null ? nameObj.toString() : null);

                    newUser.setRole("USER"); // 기본 권한
                    return userRepository.save(newUser);
                });

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())),
                attributes,
                userNameAttributeName
        );
    }
}