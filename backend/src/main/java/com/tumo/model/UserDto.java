package com.tumo.model;

public class UserDto {
	
	private int userIdx;
	private String nickname;
	private String email;
	private String password;
	private String login_type;
	private String introduce;
	private String disclosure;
	private String create_at;
	private String update_at;
	private String deleted;
	
	public UserDto(String nickname, String email, String password, String introduce) {
		super();
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.introduce = introduce;
	}

	public int getUserIdx() {
		return userIdx;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getDisclosure() {
		return disclosure;
	}

	public void setDisclosure(String disclosure) {
		this.disclosure = disclosure;
	}
	
	public String getCreate_at() {
		return create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public String getDeleted() {
		return deleted;
	}

	@Override
	public String toString() {
		return "UserDto [userIdx=" + userIdx + ", nickname=" + nickname + ", email=" + email + ", password=" + password
				+ ", login_type=" + login_type + ", introduce=" + introduce + ", disclosure=" + disclosure
				+ ", create_at=" + create_at + ", update_at=" + update_at + ", deleted=" + deleted + "]";
	}
	
}
