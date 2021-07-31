package com.tumo.model;

public class UpdateUserDto {

	// 유저정보 변경시 변경 성공 여부, 변경된 유저 정보 반환용 dto
	
	boolean success; // 변경 성공시 true, 실패시 false
	UserDto userDto;
	
	public UpdateUserDto(boolean success, UserDto userDto) {
		super();
		this.success = success;
		this.userDto = userDto;
	}

	public UpdateUserDto() {
		
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
}
