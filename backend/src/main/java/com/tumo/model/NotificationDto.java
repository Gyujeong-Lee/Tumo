package com.tumo.model;

public class NotificationDto {
	private int notificationIdx;
	private int userIdx;
	private String nickname;
	private int type;
	private int boardIdx;
	private String createAt;
	private String readAt;

	public NotificationDto() {
		super();
	}

	public NotificationDto(int notificationIdx, int userIdx, String nickname, int type, int boardIdx, String createAt,
			String readAt) {
		super();
		this.notificationIdx = notificationIdx;
		this.userIdx = userIdx;
		this.nickname = nickname;
		this.type = type;
		this.boardIdx = boardIdx;
		this.createAt = createAt;
		this.readAt = readAt;
	}

	public int getNotificationIdx() {
		return notificationIdx;
	}

	public void setNotificationIdx(int notificationIdx) {
		this.notificationIdx = notificationIdx;
	}

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getReadAt() {
		return readAt;
	}

	public void setReadAt(String readAt) {
		this.readAt = readAt;
	}

	@Override
	public String toString() {
		return "NotificationDto [notificationIdx=" + notificationIdx + ", userIdx=" + userIdx + ", nickname=" + nickname
				+ ", type=" + type + ", boardIdx=" + boardIdx + ", createAt=" + createAt + ", readAt=" + readAt + "]";
	}

}
