package com.login.user.model;

public class Gender {
	 
//    public static final String MALE = "M";
//    public static final String FEMALE = "F";
    
    private String female;
    private String male;
    public Gender() {}
    
	public Gender(String female, String male) {
		super();
		this.female = female;
		this.male = male;
	}
	public String getFemale() {
		return female;
	}
	public void setFemale(String female) {
		this.female = female;
	}
	public String getMale() {
		return male;
	}
	public void setMale(String male) {
		this.male = male;
	}
    
    
    
}