package com.login.user.validator;


import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.login.user.dao.AppUserDAO;
import com.login.user.formBean.AppUserForm;
import com.login.user.model.AppUser;
import com.login.user.service.AppUserService;

@Component
public class AppUserValidator implements Validator {

   // common-validator library.
   private EmailValidator emailValidator = EmailValidator.getInstance();

   @Autowired
   private AppUserService appUserService;

   // The classes are supported by this validator.
   @Override
   public boolean supports(Class<?> clazz) {
       return clazz == AppUserForm.class;
   }

   
   @Override
   public void validate(Object target, Errors errors) {
       AppUserForm appUserForm = (AppUserForm) target;

       System.out.println("flag in validation::::.."+appUserForm.getFlag());
       
       // Check the fields of AppUserForm.
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserForm.confirmPassword");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.appUserForm.gender");
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryCode", "NotEmpty.appUserForm.countryCode");

       if (!this.emailValidator.isValid(appUserForm.getEmail())) {
           // Invalid email.
           errors.rejectValue("email", "Pattern.appUserForm.email");
       } else if (appUserForm.getUserId() == null) {
    	   System.out.println("i am in email same....before..");  
    	   AppUser dbUser = appUserService.findAppUserByEmail(appUserForm.getEmail());
    	   
    	   
    	   
           if (dbUser != null) {
               // Email has been used by another account.
               errors.rejectValue("email", "Duplicate.appUserForm.email");
           }
       }

       if (!errors.hasFieldErrors("userName")) {
    	   System.out.println("i am in userName same....before..");  
    	   System.out.println("i am in userName getting....before.."+appUserForm.getUserName()); 
           AppUser dbUser = appUserService.findAppUserByUserName(appUserForm.getUserName());
           System.out.println("i am in userName same....after..");  
           if (dbUser != null) {
               // Username is not available.
               errors.rejectValue("userName", "Duplicate.appUserForm.userName");
           }
       }

       if (!errors.hasErrors()) {
           if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
               errors.rejectValue("confirmPassword", "Match.appUserForm.confirmPassword");
           }
       }
   }

}