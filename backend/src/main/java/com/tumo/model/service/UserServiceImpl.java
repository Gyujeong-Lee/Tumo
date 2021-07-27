package com.tumo.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumo.jwt.TokenProvider;
import com.tumo.model.LoginDto;
import com.tumo.model.SignupDto;
import com.tumo.model.TokenDto;
import com.tumo.model.UserDto;
import com.tumo.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;
	
	private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	    
    public UserServiceImpl(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
    
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

	@Transactional
	@Override
	public TokenDto login(LoginDto loginDto) {
		
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
				
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.createToken(authentication);
		
		// jwt 생성 성공시 로그인 날짜 변경
		sqlSession.getMapper(UserDao.class).updateUpdateAtByEmail(loginDto.getEmail());
		
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByEmail(loginDto.getEmail());
		userDto.setPassword(null); // Response Body에 비밀번호 노출 방지
				
		TokenDto tokenDto = new TokenDto(jwt, userDto);
		
		return tokenDto;
	}

}
