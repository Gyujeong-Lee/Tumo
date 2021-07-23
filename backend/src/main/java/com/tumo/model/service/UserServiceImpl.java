package com.tumo.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@Transactional
	public void createUser(SignupDto signupDto) throws SQLException {

		UserDto userDto = new UserDto(signupDto.getNickname(), signupDto.getEmail(), signupDto.getPassword(),
				signupDto.getIntroduce());

		// 회원가입시 비밀번호 암호화 후 DB에 저장
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);

		// 회원정보 insert
		sqlSession.getMapper(UserDao.class).createUser(userDto);

		// 회원정보 insert 성공시 태그 insert를 위한 user_idx 조회
		UserDto signupUser = sqlSession.getMapper(UserDao.class).findUserByEmail(userDto.getEmail());
		Integer userIdx = signupUser.getUserIdx();

		List<String> tagList = signupDto.getTag();

		Map<String, Object> tagMap = new HashMap<String, Object>();

		// 태그 insert
		for (String content : tagList) {
			tagMap.clear();
			tagMap.put("userIdx", userIdx);
			tagMap.put("content", content);

			// 중복되는 태그 insert 방지를 위한 검사
			if (sqlSession.getMapper(UserDao.class).findUserTagByUserIdxAndContent(tagMap)) {
				continue;
			}

			sqlSession.getMapper(UserDao.class).insertUserTag(tagMap);

		}

	}

	@Override
	public boolean checkEmail(String email) {
		// 사용 가능한 email이면 true, 중복되면 false
		boolean result = false;

		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByEmail(email);
		if (userDto == null) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean checkNickname(String nickname) {
		// 중복되면 true, 없으면 false
		boolean result = false;

		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByNickname(nickname);

		if (userDto == null) {
			result = true;
		}

		return result;
	}

}
