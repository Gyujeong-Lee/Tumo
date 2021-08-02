package com.tumo.model;

import java.util.List;

public class UpdateUserDto {

	int userIdx;
	String nickname;
	String introduce;
	String disclosure;
	List<String> tags;
	
	public int getUserIdx() {
		return userIdx;
	}
	public String getNickname() {
		return nickname;
	}
	public String getIntroduce() {
		return introduce;
	}
	public String getDisclosure() {
		return disclosure;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
