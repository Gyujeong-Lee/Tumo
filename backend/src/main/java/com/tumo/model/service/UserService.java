package com.tumo.model.service;

import java.sql.SQLException;

import com.tumo.model.LoginDto;
import com.tumo.model.SignupDto;
import com.tumo.model.TokenDto;

public interface UserService {
	
	// 회원 가입 | 회원 정보 insert
	public void createUser(SignupDto signupDto) throws SQLException;
		
	// 회원 가입 | email 중복 체크
	public boolean checkEmail(String email);
		
	// 회원 가입 | nickname 중복 체크
	public boolean checkNickname(String nickname);
	
	// 로그인 | jwt, userDto 반환
	public TokenDto login(LoginDto loginDto);
	
}
