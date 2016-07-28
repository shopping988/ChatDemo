package com.shop.model;

import com.shop.util.EnumInfoType;

public class Info {
	private String fromUser;
	private String toUser;
	private String sendTime;
	private String content;
	private EnumInfoType infoType;
	
	
	public Info(String fromUser, String toUser, String sendTime,
			String content, EnumInfoType infoType) {
		super();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.sendTime = sendTime;
		this.content = content;
		this.infoType = infoType;
	}
	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public EnumInfoType getInfoType() {
		return infoType;
	}
	public void setInfoType(EnumInfoType infoType) {
		this.infoType = infoType;
	}
	@Override
	public String toString() {
		return "Info [fromUser=" + fromUser + ", toUser=" + toUser
				+ ", sendTime=" + sendTime + ", content=" + content
				+ ", infoType=" + infoType + "]";
	}
	
}
