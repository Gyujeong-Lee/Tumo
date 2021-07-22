package com.tumo.model.dao;

import java.sql.SQLException;

import com.tumo.model.UserDto;

public interface UserDao {
	
	// 회원 가입 | 회원 정보 insert
	public void createUser(UserDto userDto) throws SQLException;
	
	// 회원 가입 | email 중복 체크
	public UserDto checkEmail(String email);
	
	// 회원 가입 | nickname 중복 체크
	public UserDto checkNickname(String nickname);
	
	

}
