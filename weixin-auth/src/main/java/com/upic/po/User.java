package com.upic.po;

import java.util.Date;


public class User {

	private String username;
	private String password;
	private String openId;
	private String phoneNum;
	private Date lastLoginTime;
	
	private String nickName;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headImagUrl;
	
	
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public User(String username, String password, String openId) {
		super();
		this.username = username;
		this.password = password;
		this.openId = openId;
	}
	public User() {
		super();
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImagUrl() {
		return headImagUrl;
	}
	public void setHeadImagUrl(String headImagUrl) {
		this.headImagUrl = headImagUrl;
	}
	
}
