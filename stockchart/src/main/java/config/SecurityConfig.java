package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll() // 홈페이지/정적 리소스 누구나 접근
                .anyRequest().authenticated() // 나머지는 로그인 필요
            )
            .oauth2Login(oauth -> oauth
                .defaultSuccessUrl("/", true) // ✅ 로그인 성공 후 홈페이지로 이동
            );

        return http.build();
    }
}