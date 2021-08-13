package com.tumo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tumo.jwt.JwtAccessDeniedHandler;
import com.tumo.jwt.JwtAuthenticationEntryPoint;
import com.tumo.jwt.JwtSecurityConfig;
import com.tumo.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		// 현재 테스트 용도로 Swagger에 대해 로그인 없이 접속 허용 시킨 상태입니다.
		web
			.ignoring()
			.antMatchers("/swagger/**", "/swagger-ui.html", 
					"/swagger-resources/**", "/webjars/**", "/v2/api-docs");	// swagger 비회원 접속 허용	
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http			
			.csrf().disable()
			
//			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			
			// 세션을 사용하지 않기 때문에 STATELESS로 설정 (서버에 상태정보 저장 안하는 형태)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and()
			.formLogin().disable() // Spring Security 기본 로그인 페이지 없애기
			.authorizeRequests()	// http 사용하는 요청 접근 제한
			.antMatchers("/**").permitAll()	
			.anyRequest().authenticated()
		
			.and()
			.apply(new JwtSecurityConfig(tokenProvider));
	}
	
}
