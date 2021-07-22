package com.tumo.model;

import java.util.List;

public class SignupDto {
	
	private String nickname;
	private String email;
	private String password;
	private String introduce;
	private List<String> tag;
	
	public String getNickname() {
		return nickname;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getIntroduce() {
		return introduce;
	}
	public List<String> getTag() {
		return tag;
	}
	
}
