package com.tumo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		
		// 현재 테스트 용도로 모든 경로에 대해 로그인 없이 접속 허용 시킨 상태입니다.
		web.ignoring().antMatchers("/**");
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().disable(); // Spring Security 기본 로그인 페이지 없애기
	}


}
