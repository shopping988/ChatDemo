package com.shop.model;

public class User {
	private String name;
	private String pwd;
	private String email;
	private int age;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String pwd, String email, int age) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.age = age;
	}

	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public User(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", pwd=" + pwd + ", email=" + email
				+ ", age=" + age + "]";
	}
	
	
}
