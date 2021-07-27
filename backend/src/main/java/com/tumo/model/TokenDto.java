package com.tumo.model;

public class TokenDto {
	
	private String jwt;
	private UserDto userDto;
	
	public TokenDto(String jwt, UserDto userDto) {
		super();
		this.jwt = jwt;
		this.userDto = userDto;
	}

	public String getJwt() {
		return jwt;
	}

	public UserDto getUserDto() {
		return userDto;
	}
	
}
