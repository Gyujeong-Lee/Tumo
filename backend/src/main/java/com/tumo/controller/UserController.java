package com.tumo.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumo.jwt.JwtFilter;
import com.tumo.model.LoginDto;
import com.tumo.model.SignupDto;
import com.tumo.model.TokenDto;
import com.tumo.model.UpdateUserDto;
import com.tumo.model.UserDto;
import com.tumo.model.service.PortfolioService;
import com.tumo.model.service.UserService;
import com.tumo.util.OauthUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private OauthUtil oauthUtil;
	
	@PostMapping(value = "/signup")
	@ApiOperation(value = "회원가입")
	public ResponseEntity createSignup(@RequestBody SignupDto signupDto) {

		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			userService.createUser(signupDto);

			// 회원가입 성공
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();

			// DB 오류로 회원가입 실패
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@GetMapping(value = "/email/{email}")
	@ApiOperation(value = "email 중복 체크")
	public ResponseEntity ReadEmail(@PathVariable String email) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();

		// 사용 가능 true, 중복 false
		boolean check = userService.checkEmail(email);

		if (check) {
			// 사용 가능한 이메일
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else {
			// 중복된 이메일 존재
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}

		return response;
	}

	@GetMapping(value = "/nickname/{nickname}")
	@ApiOperation(value = "nickname 중복 체크")
	public ResponseEntity ReadNickname(@PathVariable String nickname) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		// 사용 가능 true, 중복 false
		boolean check = userService.checkNickname(nickname);

		if (check) {
			// 사용 가능한 nickname
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else {
			// 중복된 닉네임 존재
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}

		return response;
	}
	
	@PostMapping(value = "/confirm-email")
	@ApiOperation(value = "회원가입 이메일 인증")
	public ResponseEntity createConfirmEmail(@RequestBody Map<String, Object> map) {
		int userIdx = (int) map.get("userIdx");
		String code = (String) map.get("code");
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		UserDto userDto = userService.confirmEmail(userIdx, code);
		
		if (userDto == null) {
			// 등록된 회원이 아니거나 이메일 인증이 이미 완료된 회원
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.NO_CONTENT);
			return response;
		}
		
		resultMap.put("message", "success");
		resultMap.put("email", userDto.getEmail());
		resultMap.put("nickname", userDto.getNickname());
		response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		
		return response;
	}
	
	@GetMapping(value = "/resend-confirm-email/{userIdx}")
	@ApiOperation(value = "이메일 인증코드 메일 재발송")
	public ResponseEntity readResendConfirmEmail(@PathVariable int userIdx) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		boolean check = userService.resendConfirmEmail(userIdx);
		
		if (check == false) {
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.NO_CONTENT);
			return response;
		}
	
		resultMap.put("message", "success");
		response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		
		return response;
	}
	
	@PostMapping(value = "/login")
	@ApiOperation(value = "로그인 | jwt는 헤더에 autorization으로 전송")
	public ResponseEntity createLogin(@RequestBody LoginDto loginDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		// 소셜 로그인으로 가입된 회원에게 경고 메시지 반환
		UserDto oauthCheck = userService.readUserByEmail(loginDto.getEmail());
		if (oauthCheck != null && "google".equals(oauthCheck.getOauth())) {
			resultMap.put("message", "isOauthUser");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			return response;
		}
		
		try {
			TokenDto tokenDto = userService.login(loginDto);
			
			String jwt = tokenDto.getJwt();
			UserDto userDto = tokenDto.getUserDto();
			List<String> tags = userService.readUserTag(userDto.getUserIdx());

			if ("temp".equals(userDto.getLogin_type())) {
				// 이메일 인증 안된 임시 회원
				resultMap.put("message", "temp");
				resultMap.put("userIdx", userDto.getUserIdx());
				resultMap.put("email", userDto.getEmail());
				
				response = new ResponseEntity<>(resultMap, HttpStatus.OK);
				return response;
			}
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			
			resultMap.put("userDto", userDto);
			resultMap.put("tags", tags);
			resultMap.put("message", "success");
			
			response = new ResponseEntity<>(resultMap, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping(value = "/checkpassword")
	@ApiOperation(value = "비밀번호 확인")
	public ResponseEntity createCheckpassword(@RequestBody UserDto userDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		boolean check = userService.checkPassword(userDto.getUserIdx(), userDto.getPassword());
		
		if (check) {
			// 비밀번호 일치
			resultMap.put("message", "success");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		} else {
			// 비밀번호 불일치
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PutMapping(value = "/password")
	@ApiOperation(value = "비밀번호 변경")
	public ResponseEntity updatePassword(@RequestBody UserDto userDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		if ( userService.updatePassword(userDto.getUserIdx(), userDto.getPassword()) ) {
			// 비밀번호 변경 성공
			resultMap.put("message", "success");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		} else {
			// 기존과 동일한 비밀번호 변경 시도로 인한 실패
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PutMapping(value = "/update")
	@ApiOperation(value = "회원정보 변경 (자기소개, 관심사 태그, 닉네임, 공개 여부")
	public ResponseEntity updateUpdate(@RequestBody UpdateUserDto updateUserDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			// 회원정보
			UserDto userDto = userService.updateUser(updateUserDto);
			List<String> tags = userService.readUserTag(userDto.getUserIdx());
			
			resultMap.put("message", "success");
			resultMap.put("userDto", userDto);
			resultMap.put("tags", tags);
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			// DB 오류
			e.printStackTrace();
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
		
	@DeleteMapping(value = "/delete")
	@ApiOperation(value = "회원 탈퇴")
	public ResponseEntity deleteUser(@RequestBody Map<String, Integer> map) {
		int userIdx = map.get("userIdx");
		
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		userService.deleteUser(userIdx);
		resultMap.put("message", "success");
		response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		
		return response;
	}
	
	@GetMapping(value = "/findpwd/{email}")
	@ApiOperation(value = "비밀번호 찾기")
	public ResponseEntity createFindpwd(@PathVariable String email) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		// 소셜 로그인으로 가입된 회원에게 경고 메시지 반환
		UserDto oauthCheck = userService.readUserByEmail(email);
		if (oauthCheck != null && "google".equals(oauthCheck.getOauth())) {
			resultMap.put("message", "isOauthUser");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			return response;
		}
		
		boolean check = userService.findPassword(email);
		
		if (check) {
			// 임시비밀번호 메일 전송 성공
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else {
			// 실패사유
			// 1. 전송받은 email이 회원 DB에 존재하지않음
			// 2. MailJet 1일 메일 전송 제한 200회 초과
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@GetMapping(value = "/updaterank")
	@ApiOperation(value = "랭크 및 수익률 업데이트")
	public ResponseEntity updateRank() {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		boolean check = portfolioService.updateRank();
		if(check) {
			check=portfolioService.updateYield();
		}
			if (check) {
				resultMap.put("message", "success");
				response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			} else {

				resultMap.put("message", "fail");
				response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}
		
		return response;
	}
	
	@PostMapping(value = "/oauth-login")
	@ApiOperation(value = "소셜 로그인")
	public ResponseEntity createOauthLogin(@RequestBody String idToken) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		String email = oauthUtil.tokenVerify(idToken);
		UserDto oauthCheck = userService.readUserByEmail(email);
		
		if (oauthCheck == null) {
			// 가입되지 않은 이메일의 경우 회원 가입 페이지로 이동
			resultMap.put("message", "null");
			resultMap.put("email", email);
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			return response;
		}
		
		if (!"google".equals(oauthCheck.getOauth())) {
			// 일반 회원가입 회원이 소셜 로그인 시도
			resultMap.put("message", "isNotOauthUser");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			return response;
		}
		
		try {
			// 소셜 로그인 회원의 경우 비밀번호 "google1234", "naver1234", "kakao1234" 등으로 고정
			LoginDto loginDto = new LoginDto(email, "google1234");
			TokenDto tokenDto = userService.login(loginDto);
			
			String jwt = tokenDto.getJwt();
			UserDto userDto = tokenDto.getUserDto();
			List<String> tags = userService.readUserTag(userDto.getUserIdx());

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			
			resultMap.put("userDto", userDto);
			resultMap.put("tags", tags);
			resultMap.put("message", "success");
			
			response = new ResponseEntity<>(resultMap, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping(value = "/oauth-signup")
	@ApiOperation(value = "소셜 로그인으로 회원가입")
	public ResponseEntity createOauthSignup(@RequestBody SignupDto signupDto) {

		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			userService.createOauthUser(signupDto);

			// 회원가입 성공
			resultMap.put("message", "success");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
		} catch (SQLException e) {
			e.printStackTrace();

			// DB 오류로 회원가입 실패
			resultMap.put("message", "fail");
			response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
	
}
