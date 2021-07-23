package com.tumo.model.dao;

import java.sql.SQLException;
import java.util.Map;

import com.tumo.model.UserDto;

public interface UserDao {
	
	// 회원 정보 insert
	public void createUser(UserDto userDto) throws SQLException;
	
	// email로 회원 조회
	public UserDto findUserByEmail(String email);
	
	// nickname으로 회원 조회
	public UserDto findUserByNickname(String nickname);
	
	// user_tag insert
	public void insertUserTag(Map<String, Object> tagMap);
	
	// user_tag 중복 조회
	public boolean findUserTagByUserIdxAndContent(Map<String, Object> tagMap);
	
}
