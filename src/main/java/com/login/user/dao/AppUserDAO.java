package com.login.user.dao;
 






import java.util.List;


import com.login.user.model.AppUser;

public interface AppUserDAO {
 
	 
	   AppUser findAppUserByEmail(String email);
	  
	   AppUser findAppUserByUserName(String userName);
	 	
	   AppUser saveAppUser(AppUser person);
	 	   
	   void updateMultipleUsers();
	  
	   AppUser updateOneUser(AppUser person);
	   
	   void deleteUser(AppUser user);
	   List<AppUser> getAllUsers();
	   
	   
 
 }