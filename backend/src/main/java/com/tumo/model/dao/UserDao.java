package com.tumo.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tumo.model.UpdateUserDto;
import com.tumo.model.UserDto;

public interface UserDao {
	
	// 회원 정보 insert
	public void createUser(UserDto userDto) throws SQLException;
	
	// email 인증 코드 insert
	public void insertUserTemp(Map<String, Object> tempMap);
	
	// email 인증 코드 조회
	public String findUserTempByUserIdx(int userIdx);
	
	// email 인증 완료 (login_type을 ROLE_USER로 변경) & user_temp 컬럼 삭제
	public void updateUserLoginType(int userIdx);
	
	// email로 회원 조회
	public UserDto findUserByEmail(String email);
	
	// nickname으로 회원 조회
	public UserDto findUserByNickname(String nickname);
	
	// userIdx로 회원 조회
	public UserDto findUserByUserIdx(int userIdx);
		
	// user_tag insert
	public void insertUserTag(Map<String, Object> tagMap);
	
	// user_tag 중복 조회
	public boolean findUserTagByUserIdxAndContent(Map<String, Object> tagMap);
	
	// user_tag 조회
	public List<String> findUserTagByUserIdx(int userIdx);
	
	// 로그인시 update_at 갱신
	public void updateUpdateAtByEmail(String email);
	
	// password 변경
	public void updatePasswordByUserIdx(Map<String, Object> map);
	
	// 회원정보(introduce, nickname, disclosure, tags) 변경
	public void updateUserByUserIdx(UpdateUserDto updateUserDto);
	
	// 회원 탈퇴
	public void deleteUserByUserIdx(int userIdx);
	
}
