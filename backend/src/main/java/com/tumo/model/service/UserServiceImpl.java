package com.tumo.model.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.model.SignupDto;
import com.tumo.model.UserDto;
import com.tumo.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;

	// Bcrypt 해시 함수를 이용한 비밀번호 암호화
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	@Transactional(rollbackFor = SQLException.class)
	public void createUser(SignupDto signupDto) throws SQLException {
		
		UserDto userDto = new UserDto(signupDto.getNickname(), signupDto.getEmail(),
				signupDto.getPassword(), signupDto.getIntroduce());
		
		// 회원가입시 비밀번호 암호화 후 DB에 저장
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);

		sqlSession.getMapper(UserDao.class).createUser(userDto);

	}

	@Override
	public boolean checkEmail(String email) {
		// 사용 가능한 email이면 true, 중복되면 false
		boolean result = false;

		UserDto userDto = sqlSession.getMapper(UserDao.class).checkEmail(email);
		if (userDto == null) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean checkNickname(String nickname) {
		// 중복되면 true, 없으면 false
		boolean result = false;

		UserDto userDto = sqlSession.getMapper(UserDao.class).checkNickname(nickname);

		if (userDto == null) {
			result = true;
		}

		return result;
	}

}
