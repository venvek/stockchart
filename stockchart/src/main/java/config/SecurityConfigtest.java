package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigtest {
	 // 사용자 정의 SecurityFilterChain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF 보호 비활성화 (필요한 경우)
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()  // /public 경로는 인증 없이 접근 가능
                .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
            .and()
            .formLogin()
                .loginPage("/login")  // 커스텀 로그인 페이지 경로
                .permitAll()
            .and()
            .logout()
                .permitAll();  // 로그아웃은 누구나 접근 가능

        return http.build();
    }

    // 인메모리 사용자 세부 정보 서비스 설정
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // 사용자 정의 (username, password, roles) 설정
        var user1 = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        var admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, admin);
    }

    // 비밀번호 암호화를 위한 PasswordEncoder 설정 (BCrypt 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}