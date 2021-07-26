package com.tumo.model.dao;

public class ProfileDto {
	private int userIdx;
	private String nickname;
	private String introduce;
	private int followingCnt;
	private int followerCnt;

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getFollowingCnt() {
		return followingCnt;
	}

	public void setFollowingCnt(int followingCnt) {
		this.followingCnt = followingCnt;
	}

	public int getFollowerCnt() {
		return followerCnt;
	}

	public void setFollowerCnt(int followerCnt) {
		this.followerCnt = followerCnt;
	}
	
	

	public ProfileDto(int userIdx, String nickname, String introduce) {
		super();
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.introduce = introduce;
	}

	public ProfileDto(int userIdx, String nickname, String introduce, int followingCnt, int followerCnt) {
		super();
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.introduce = introduce;
		this.followingCnt = followingCnt;
		this.followerCnt = followerCnt;
	}

	@Override
	public String toString() {
		return "profileDto [userIdx=" + userIdx + ", nickname=" + nickname + ", introduce=" + introduce
				+ ", followingCnt=" + followingCnt + ", followerCnt=" + followerCnt + "]";
	}

}
