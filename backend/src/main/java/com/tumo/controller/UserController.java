package com.tumo.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.tumo.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

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
	
	@PostMapping(value = "/login")
	@ApiOperation(value = "로그인 | jwt는 헤더에 autorization으로 전송")
	public ResponseEntity createLogin(@RequestBody LoginDto loginDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
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
	
	@PutMapping(value = "/nickname")
	@ApiOperation(value = "닉네임 변경")
	public ResponseEntity updateNickname(@RequestBody UserDto userDto) {
		ResponseEntity response = null;
		Map<String, Object> resultMap = new HashMap<>();
		
		UpdateUserDto updateUserDto = userService.updateNickname(
				userDto.getUserIdx(), userDto.getNickname());
		
		if ( updateUserDto.getSuccess() ) {
			// 닉네임 변경 성공
			List<String> tags = userService.readUserTag(userDto.getUserIdx());
			resultMap.put("tags", tags);
			resultMap.put("message", "success");
			resultMap.put("userDto", updateUserDto.getUserDto());
			
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		} else {
			// 중복된 닉네임 변경 시도로 인한 실패
			resultMap.put("message", "fail");
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return response;
	}
	
	@DeleteMapping(value = "/user/{userIdx}")
	@ApiOperation(value = "회원 탈퇴")
	public ResponseEntity deleteUser(@PathVariable int userIdx) {
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
	
}
