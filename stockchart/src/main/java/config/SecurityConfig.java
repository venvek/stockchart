package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/main", "/login", "/recommend").permitAll()  // 누구나 접근 가능
                .requestMatchers("/indicator", "/sector", "/ohlc").authenticated() // 로그인 필요
                .anyRequest().authenticated() // 그 외 모든 요청도 로그인 필요
            )
            .formLogin(login -> login
                .loginPage("/login") // 커스텀 로그인 페이지 설정
                .defaultSuccessUrl("/main", true) // 로그인 성공 시 이동할 페이지
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .httpBasic(); // HTTP Basic 인증 활성화 (API 사용 시 필요)

        return http.build();
    }
}