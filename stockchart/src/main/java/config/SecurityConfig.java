package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // 모든 요청은 인증 필요
            )
            // ✅ 스프링 제공 기본 로그인 폼 사용
            .formLogin(Customizer.withDefaults())
            .logout(Customizer.withDefaults()); 

        return http.build();
    

        
		/*
		 * http .authorizeHttpRequests(auth -> auth .requestMatchers("/", "/login",
		 * "/css/**", "/js/**").permitAll() .anyRequest().authenticated() ) // ✅ 일반 로그인
		 * 폼 활성화 .formLogin(form -> form .loginPage("/login") // 커스텀 로그인 페이지 URL
		 * .permitAll() ) // ✅ 소셜 로그인 활성화 .oauth2Login(oauth2 -> oauth2
		 * .loginPage("/login") ) .logout(logout -> logout.permitAll());
		 * 
		 * return http.build(); }
		 */
    }
}