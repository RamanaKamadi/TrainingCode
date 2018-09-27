package com.login.user.formBean;

import java.util.List;

public class LoginForm {

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> language;
	private String flag;
	
	public LoginForm() {}
	
	public LoginForm(String userName, String password,String firstName,String lastName,String email,List<String> language) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
