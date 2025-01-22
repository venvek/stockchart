package config;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	  throws Exception { http .csrf().disable() // CSRF 보호 비활성화 (필요한 경우)
	  .formLogin() .loginPage("/login"); // 커스텀 로그인 페이지 경로 .permitAll() .and()
	  
	  return http.build(); }
	 
	  // 비밀번호 암호화를 위한 PasswordEncoder 설정 (BCrypt 사용)
	  
	  @Bean public PasswordEncoder passwordEncoder() { return new
	  BCryptPasswordEncoder(); }
}
