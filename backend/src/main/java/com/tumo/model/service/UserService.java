package com.tumo.model.service;

import java.sql.SQLException;
import java.util.List;

import com.tumo.model.LoginDto;
import com.tumo.model.SignupDto;
import com.tumo.model.TokenDto;
import com.tumo.model.UpdateUserDto;
import com.tumo.model.UserDto;

public interface UserService {
	
	// 회원가입 | 회원 정보 insert
	public void createUser(SignupDto signupDto) throws SQLException;
		
	// email 중복 체크
	public boolean checkEmail(String email);
		
	// nickname 중복 체크
	public boolean checkNickname(String nickname);
	
	// 회원가입 이메일 인증
	public UserDto confirmEmail(int userIdx, String code);
	
	// 이메일 인증코드 메일 재발송
	public boolean resendConfirmEmail(int userIdx);
	
	// 로그인 | jwt, userDto 반환
	public TokenDto login(LoginDto loginDto);
	
	// 유저 태그 조회 | 로그인 및 회원 정보 변경시 새로 조회해서 갱신
	public List<String> readUserTag(int userIdx);
	
	// 비밀번호 확인 | password 일치 확인
	public boolean checkPassword(int userIdx, String password);
	
	// 비밀번호 변경 | password 변경
	public boolean updatePassword(int userIdx, String password);
	
	// 회원정보 수정 | introduce, nickname, disclosure, tags 변경
	public UserDto updateUser(UpdateUserDto updateUserDto);
	
	// 회원 탈퇴
	public void deleteUser(int userIdx);
	
	// 비밀번호 찾기 (임시 비밀번호 8자리 생성)
	public boolean findPassword(String email);
}
