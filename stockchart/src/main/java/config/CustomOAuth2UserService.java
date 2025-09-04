package config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jucha.stockchart.User;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google/naver/kakao
        String userNameAttribute = userRequest.getClientRegistration()
                                              .getProviderDetails()
                                              .getUserInfoEndpoint()
                                              .getUserNameAttributeName();

        String oauthId = oAuth2User.getAttribute(userNameAttribute);

        // ✅ user 변수 선언 필수
        User user = userRepository.findByProviderAndProviderId(registrationId, oauthId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setProvider(registrationId);
                    newUser.setProviderId(oauthId);
                    newUser.setEmail(oAuth2User.getAttribute("email"));
                    newUser.setName(oAuth2User.getAttribute("name"));
                    newUser.setRole("USER"); // 기본 USER
                    return userRepository.save(newUser);
                });

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())),
                oAuth2User.getAttributes(),
                userNameAttribute
        );
    }
    }

