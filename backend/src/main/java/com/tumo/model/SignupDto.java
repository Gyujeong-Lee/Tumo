package com.tumo.model;

import java.util.List;

public class SignupDto {
	
	private String nickname;
	private String email;
	private String password;
	private String introduce;
	private String oauth;
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
	public String getOauth() {
		return oauth;
	}
	public List<String> getTag() {
		return tag;
	}
	
}
