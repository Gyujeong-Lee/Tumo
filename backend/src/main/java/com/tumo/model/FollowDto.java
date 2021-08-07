package com.tumo.model;

public class FollowDto {

	private int userIdx;
	private int followingIdx;

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public int getFollowingIdx() {
		return followingIdx;
	}

	public void setFollowingIdx(int otheruserIdx) {
		this.followingIdx = otheruserIdx;
	}

	public FollowDto() {
		super();
	}

	public FollowDto(int userIdx, int followingIdx) {
		super();
		this.userIdx = userIdx;
		this.followingIdx = followingIdx;
	}

	@Override
	public String toString() {
		return "FollowDto [userIdx=" + userIdx + ", followingIdx=" + followingIdx + "]";
	}

}
