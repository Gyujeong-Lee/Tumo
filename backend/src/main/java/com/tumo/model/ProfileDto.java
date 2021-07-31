package com.tumo.model;

public class ProfileDto {
	private int userIdx;
	private String nickname;
	private String introduce;
	private int followingCnt;
	private int followerCnt;
	private String disclosure;

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

	public String getDisclosure() {
		return disclosure;
	}

	public void setDisclosure(String disclosure) {
		this.disclosure = disclosure;
	}

	public ProfileDto(int userIdx, String nickname, String introduce, String disclosure) {
		super();
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.introduce = introduce;
		this.disclosure = disclosure;
	}

	public ProfileDto(int userIdx, String nickname, String introduce, int followingCnt, int followerCnt,
			String disclosure) {
		super();
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.introduce = introduce;
		this.followingCnt = followingCnt;
		this.followerCnt = followerCnt;
		this.disclosure = disclosure;
	}

	@Override
	public String toString() {
		return "ProfileDto [userIdx=" + userIdx + ", nickname=" + nickname + ", introduce=" + introduce
				+ ", followingCnt=" + followingCnt + ", followerCnt=" + followerCnt + ", disclosure=" + disclosure
				+ "]";
	}

}
