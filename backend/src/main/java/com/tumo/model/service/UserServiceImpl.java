package com.tumo.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.tumo.jwt.TokenProvider;
import com.tumo.model.LoginDto;
import com.tumo.model.SignupDto;
import com.tumo.model.TokenDto;
import com.tumo.model.UpdateUserDto;
import com.tumo.model.UserDto;
import com.tumo.model.dao.UserDao;
import com.tumo.util.MailUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private MailUtil mailUtil;
		
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
	public void createOauthUser(SignupDto signupDto) throws SQLException {

		UserDto userDto = new UserDto(signupDto.getNickname(), signupDto.getEmail(), signupDto.getOauth());
		userDto.setLogin_type("ROLE_USER");
		
		// DB 테이블 비밀번호 컬럼이 NOT NULL이라서 초기화는 하되 사용은 하지않습니다
		String encodedPassword = passwordEncoder.encode("google1234");
		userDto.setPassword(encodedPassword);
		
		// 회원정보 insert
		sqlSession.getMapper(UserDao.class).createOauthUser(userDto);

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
		
		// 이메일 인증 코드 생성후 이메일 발송
		String code = mailUtil.getTempPassword();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("userIdx", userIdx);
		tempMap.put("code", code);
		sqlSession.getMapper(UserDao.class).insertUserTemp(tempMap);
		
		try {
			mailUtil.confirmEmail(signupUser.getEmail(), signupUser.getNickname(), userIdx, code);
		} catch (MailjetException e) {
			e.printStackTrace();
		} catch (MailjetSocketTimeoutException e) {
			e.printStackTrace();
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
		// 사용 가능한 nickname이면 true, 안면 false
		boolean result = false;

		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByNickname(nickname);

		if (userDto == null) {
			result = true;
		}

		return result;
	}
	
	@Transactional
	@Override
	public UserDto confirmEmail(int userIdx, String code) {
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByUserIdx(userIdx);
		String tempCode = sqlSession.getMapper(UserDao.class).findUserTempByUserIdx(userIdx);
		
		if ( userDto == null || tempCode == null || !code.equals(tempCode) ) {
			// 실패사유
			// 1. 등록된 회원이 아님
			// 2. 이미 인증 완료된 회원
			// 3. 입력받은 인증 코드가 틀린 경우
			return null;
		}
		
		sqlSession.getMapper(UserDao.class).updateUserLoginType(userIdx);
		
		return userDto;
	}
	
	@Override
	public boolean resendConfirmEmail(int userIdx) {
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByUserIdx(userIdx);
		String code = sqlSession.getMapper(UserDao.class).findUserTempByUserIdx(userIdx);
		
		if ( userDto == null || code == null ) {
			return false;
		}
		
		try {
			mailUtil.confirmEmail(userDto.getEmail(), userDto.getNickname(), userIdx, code);
		} catch (MailjetException | MailjetSocketTimeoutException e) {
			e.printStackTrace();
		}
		
		return true;
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
	
	@Override
	public List<String> readUserTag(int userIdx) {
		return sqlSession.getMapper(UserDao.class).findUserTagByUserIdx(userIdx);
	}

	@Override
	public boolean checkPassword(int userIdx, String password) {
		// 비밀번호 일치하면 true, 불일치하면 false
		boolean result = false;
		
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByUserIdx(userIdx);
		
		// 비밀번호 일치 검사
		if ( passwordEncoder.matches(password, userDto.getPassword()) ) {
			result = true;
		}
		
		return result;
	}
	
	@Override
	public boolean updatePassword(int userIdx, String password) {
		// 비밀번호 변경 성공하면 true, 기존과 동일한 비밀번호 변경 시도할 경우 false;
		boolean result = false;
		
		if (checkPassword(userIdx, password)) {
			// 변경하려는 비밀번호가 DB에 저장된 기존 비밀번호와 일치
			result = false;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userIdx", userIdx);
			map.put("password", passwordEncoder.encode(password));
			
			sqlSession.getMapper(UserDao.class).updatePasswordByUserIdx(map);
			result = true;
		}
		
		return result;
	}
	
	@Transactional
	@Override
	public UserDto updateUser(UpdateUserDto updateUserDto) {
		// treeSet 이용한 tags 중복 제거
		TreeSet<String> tagsTreeSet = new TreeSet<String>();
		
		for(String tag : updateUserDto.getTags()) {
			tagsTreeSet.add(tag);
		}
		
		List<String> tagList = new ArrayList<String>(tagsTreeSet);
		updateUserDto.setTags(tagList);
		
		sqlSession.getMapper(UserDao.class).updateUserByUserIdx(updateUserDto);
		
		// 변경된 회원 정보 반환
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByUserIdx(updateUserDto.getUserIdx());
		return userDto;
	}

	@Override
	public void deleteUser(int userIdx) {
		sqlSession.getMapper(UserDao.class).deleteUserByUserIdx(userIdx);
	}

	@Transactional
	@Override
	public boolean findPassword(String email) {
		boolean result = false; // 임시 비밀번호 생성시 true, 실패시 false 반환
		
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByEmail(email);
				
		if (userDto == null) {
			// 작성한 email로 가입된 회원이 존재하지 않을 경우 false 반환
			result = false;
			return result;
		}
		
		// 8자리 숫자+영문자 임시 비밀번호 생성
		String tempPassword = mailUtil.getTempPassword();
		
		// DB 회원 정보를 임시 비밀번호로 갱신
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userIdx", userDto.getUserIdx());
		map.put("password", passwordEncoder.encode(tempPassword));
		sqlSession.getMapper(UserDao.class).updatePasswordByUserIdx(map);
		
		try {
			String status = mailUtil.findPassword(email, userDto.getNickname(), tempPassword);
			
			if (status.equals("200")) {
				// 임시 비밀번호 메일 전송 성공
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public UserDto readUserByEmail(String email) {
		UserDto userDto = sqlSession.getMapper(UserDao.class).findUserByEmail(email);
		
		if (userDto == null) {
			return null;
		}
		
		return userDto;
	}
	
}
