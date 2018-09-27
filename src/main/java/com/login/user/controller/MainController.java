package com.login.user.controller;


import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.login.user.dao.AppUserDAO;
import com.login.user.dao.CountryDAO;
import com.login.user.formBean.AppUserForm;
import com.login.user.formBean.LoginForm;
import com.login.user.helper.PasswordEncAndDec;
import com.login.user.model.AppUser;
import com.login.user.model.Country;
import com.login.user.model.Gender;
import com.login.user.service.AppUserService;
import com.login.user.service.EmailService;
import com.login.user.validator.AppUserValidator;
 
@Controller
public class MainController {

   @Autowired
//   @Qualifier("appUserService")
  private AppUserService appUserService;
 
   @Autowired
  private CountryDAO countryDAO;
  @Autowired
   private AppUserValidator appUserValidator;
   
   @Autowired
   private PasswordEncAndDec passwordDecoder;
  @Autowired
//  @Qualifier("emailService")
 private EmailService emailService;
 
	//inject the beans through Constructor
//	private AppUserService appUserService;
//	 private CountryDAO countryDAO;
//	 private AppUserValidator appUserValidator;
//	  private PasswordEncAndDec passwordDecoder;
//	  private EmailService emailService;
//	@Autowired
//    public MainController(AppUserService appUserService,CountryDAO countryDAO, AppUserValidator appUserValidator, PasswordEncAndDec passwordDecoder,EmailService emailService) {
//      
//      this.appUserService = appUserService;
//      this.countryDAO = countryDAO;
//      this.appUserValidator = appUserValidator;
//      this.passwordDecoder = passwordDecoder;
//      this.emailService = emailService;
//      
//    }
//	
	
   // Set a form validator
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
      // Form target
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == AppUserForm.class) {
    	 
    
        // dataBinder.setValidator(appUserValidator);
      }
      // ...
   }
 
   @RequestMapping("/")
   public String viewHome(Model model) {
 
      return "welcomePage";
   }
 
   @RequestMapping("/userDetails")
   public String viewMembers(Model model) {
 
      List<AppUser> list = appUserService.getAppUsers();
 
      model.addAttribute("members", list);
 
      return "usersDetails";
   }
 
   @RequestMapping("/registerSuccessful")
   public String viewRegisterSuccessful(Model model,HttpSession session) {
	   System.out.println("success:::::::"+session.getAttribute("flag"));
	   
	   model.addAttribute("mode", session.getAttribute("flag"));
	   
      return "registerSuccessfulPage";
   }
 
   // Show Register page.
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String viewRegister(Model model) {
 
      AppUserForm form = new AppUserForm();
      List<Country> countries = countryDAO.getCountries();
     
//      List<String> gender = new ArrayList<>();
//      gender.add("F");
//      gender.add("M");
     Map<String,String> genderList=new HashMap<>();
     genderList.put("Female", "F");
     genderList.put("Male", "M");
    List<String> languageList=new ArrayList<>();
    languageList.add("English");
    languageList.add("Hindi");
    languageList.add("Telugu");
    languageList.add("Tamil");
    
    System.out.println("flag value:::Registration::::"+form.getFlag());
      model.addAttribute("appUserForm", form);
      model.addAttribute("countries", countries);
      model.addAttribute("genderList", genderList);
      model.addAttribute("languageList", languageList);
      model.addAttribute("flag", "Reg");
      return "registerPage";
   }
 
   // This method is called to save the registration information.
   // @Validated: To ensure that this Form
   // has been Validated before this method is invoked.
   @RequestMapping(value = "/register", method = RequestMethod.POST,params="action=Submit")
   public String saveRegister(Model model, @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, BindingResult result,final RedirectAttributes redirectAttributes,HttpServletRequest request) {
 
      // Validate result
      if (result.hasErrors()) {
    	  Map<String,String> genderList=new HashMap<>();
    	     genderList.put("Female", "F");
    	     genderList.put("Male", "M");
    	    List<String> languageList=new ArrayList<>();
    	    languageList.add("English");
    	    languageList.add("Hindi");
    	    languageList.add("Telugu");
    	    languageList.add("Tamil");
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         model.addAttribute("languageList", languageList);
         model.addAttribute("genderList", genderList);
        
         model.addAttribute("flag", "Reg");
         
        
         return "registerPage";
      }
      AppUser newUser= null;
      try {

         
         //registration mail
       //*******************************************************************************************************
      // Generate random 36-character string token for confirmation link
       //         appUserForm.setConfirmationToken(UUID.randomUUID().toString());
		        
         newUser = appUserService.createAppUser(appUserForm);
				
//			String appUrl = request.getScheme() + "://" + request.getServerName();
//			System.out.println("appurl:::::"+appUrl);
			
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(appUserForm.getEmail());
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("Your Registration successfully completed\n"+"Login Details:\n"+"UserName: "+appUserForm.getUserName()+"\nPassword: "+appUserForm.getPassword()+"\nNote:Should not share your Login Details with others.");
			registrationEmail.setFrom("rama58kamadi@gmail.com");
			
			emailService.sendEmail(registrationEmail);
									
			model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + appUserForm.getEmail());
			
	//*******************************************************************************************************
         
         
         
      }
      // Other error!!
      catch (Exception e) {
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }
   
   
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String doLogin(Model model) {
 
	   LoginForm form = new LoginForm();
	   model.addAttribute("appUserForm", form);
	   model.addAttribute("flag", "log");
	      return "login";
   }
 
   @RequestMapping(value = "/login", method = RequestMethod.POST)
  
  public String loginSuccess(Model model, @ModelAttribute("appUserForm") @Validated LoginForm appUserForm, BindingResult result,final RedirectAttributes redirectAttributes,HttpSession session) {
		   
		      // Validate result
	  
		      AppUser newUser= null;
		      try {
		    	  
		    	  String username=appUserForm.getUserName();
		    	  String password=appUserForm.getPassword();
		    	  System.out.println("form passwords.out...."+appUserForm.getPassword());
		         newUser = appUserService.findAppUserByUserName(username);
		         session.setAttribute("userName", username);
		       
		         if (newUser == null) {
		        	 
		        	 System.out.println("null user....."+newUser);
		             // Username is not available.
		           	 result.rejectValue("userName", "notFound.appUserForm.userName");
		         
		  		         return "login";
		  		      }else if (password=="") 
		  		      {
		  		    	 	System.out.println("form passwords....."+appUserForm.getPassword());
		  		    		 result.rejectValue("password", "NotEmpty.appUserForm.password");
		  		    		return "login";
		  		    	  }
		  		      else {
		  		    	System.out.println("equal passwords..before....");
		  		    String dbpsw=this.passwordDecoder.passwordDencoder(newUser.getEncrytedPassword());
		  		 	System.out.println("equal passwords.db.before...."+dbpsw);
		  		 	System.out.println("equal passwords..java..before...."+appUserForm.getPassword());
		  		 	
		  		    if(appUserForm.getPassword().equals(dbpsw)) {
		  		   
		  		    	System.out.println("equal passwords......");
		  		   	System.out.println("email:::."+newUser.getEmail());
		  		    	
	  		    	model.addAttribute("firstName", newUser.getFirstName());
  		   	model.addAttribute("lastName", newUser.getLastName());
	  		 	model.addAttribute("email", newUser.getEmail());
	  		 	model.addAttribute("language", newUser.getLanguage());
	  		 	
		  		 	 model.addAttribute("newUser", newUser); 
		  		 	 System.out.println("flag value:::::"+appUserForm.getFlag());
		  		 	 if("upd".equals(appUserForm.getFlag())) {
		  		 		 System.out.println("flag value::inside:::"+appUserForm.getFlag());
		  		 		 
		  		 		redirectAttributes.addFlashAttribute("flashUser", newUser);
		  		 		model.addAttribute("flag","log");
		  		 	  session.setAttribute("flag", "upd");
		  		 		 return "redirect:/updateform";
		  		 	 }
		  		 	 else
		  		    {return "loginSuccess";}
		  		        
		  		    }else {
		  		        	 result.rejectValue("password", "Match.appUserForm.password");
			  		    		//return "login";
		  		         }
		  		      }
		      }
		      // Other error!!
		      catch (Exception e) {
		        
		         model.addAttribute("errorMessage", "Error: " + e.getMessage());
		         return "login";
		      }
		 
		      redirectAttributes.addFlashAttribute("flashUser", newUser);
		       
		      return "login";
		   }
	//***********************************************************************************************************************************8
               
        
   
     // udate page
   @RequestMapping(value = "/update", method = RequestMethod.GET)
   public String updateDetails(Model model) {
 
	   LoginForm form = new LoginForm();
	   model.addAttribute("appUserForm", form);
	   model.addAttribute("flag", "upd");
	   form.setFlag("upd");
	   return "login";

 
      
   }
   
   
   @RequestMapping(value = "/updateform", method = RequestMethod.GET)
   public String updatePage(Model model,HttpSession session) {
	  
	  System.out.println("session username:::::"+ session.getAttribute("userName"));
	   String userName=(String)session.getAttribute("userName");
	   AppUser newUser = appUserService.findAppUserByUserName(userName);
	  
     
	   
      AppUserForm form = new AppUserForm();
     List<Country> countries = countryDAO.getCountries();
     
     List<String> gender = new ArrayList<>();
     gender.add("F");
     gender.add("M");
     Map<String,String> genderList=new HashMap<>();
     genderList.put("Female", "F");
     genderList.put("Male", "M");
   List<String> languageList=new ArrayList<>();
   languageList.add("English");
    languageList.add("Hindi");
   languageList.add("Telugu");
    languageList.add("Tamil");
    
   System.out.println("flag value:::Registration::::"+form.getFlag());
    model.addAttribute("appUserForm", form);
     model.addAttribute("countries", countries);
      model.addAttribute("genderList", genderList);
      model.addAttribute("languageList", languageList);
     
      
   // to set related user details.........
      
      form.setUserName(newUser.getUserName());
      form.setEmail(newUser.getEmail());
      form.setFirstName(newUser.getFirstName());
      form.setLastName(newUser.getLastName());
      form.setGender(newUser.getGender());
      form.setCountryCode(newUser.getCountryCode());
      form.setLanguage(newUser.getLanguage());
      
      
      model.addAttribute("flag", "log");
      return "registerPage";
   }
   
   
   
   @RequestMapping(value = "/register", method = RequestMethod.POST,params="action=Update")
   public String submitUpadedData(Model model, @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, BindingResult result,final RedirectAttributes redirectAttributes,HttpServletRequest request,HttpSession session ) {
	  
	   appUserForm.setFlag( session.getAttribute("flag").toString());
	   System.out.println("flag in update button:"+appUserForm.getFlag());

	   appUserForm.setFlag( session.getAttribute("flag").toString());
      // Validate result
      if (result.hasErrors()) {
    	  Map<String,String> genderList=new HashMap<>();
    	     genderList.put("Female", "F");
    	     genderList.put("Male", "M");
    	    List<String> languageList=new ArrayList<>();
    	    languageList.add("English");
    	    languageList.add("Hindi");
    	    languageList.add("Telugu");
    	    languageList.add("Tamil");
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         model.addAttribute("languageList", languageList);
         model.addAttribute("genderList", genderList);
        
         model.addAttribute("flag", "upd");
        
         //appUserService.findAppUserByUserName(username);
         session.getAttribute("flag");
         System.out.println("flag in update registration::request::"+session.getAttribute("flag"));
        
         System.out.println("flag in update registration::::"+appUserForm.getFlag());
         return "registerPage";
      }
      AppUser newUser= null;
      try {

         
        
       //*******************************************************************************************************
    	  //registration mail
		        
         newUser = appUserService.UpdateAppUser(appUserForm);
				

			
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(appUserForm.getEmail());
			registrationEmail.setSubject("Update user details");
			registrationEmail.setText("Your Data has been updated successfully\n"+"Login Details:\n"+"UserName: "+appUserForm.getUserName()+"\nPassword: "+appUserForm.getPassword()+"\nNote:Should not share your Login Details with others.");
			registrationEmail.setFrom("rama58kamadi@gmail.com");
			
			emailService.sendEmail(registrationEmail);
									
			model.addAttribute("confirmationMessage", "A confirmation e-mail has been sent to " + appUserForm.getEmail());
			
	//*******************************************************************************************************
         
         
         
      }
      // Other error!!
      catch (Exception e) {
         List<Country> countries = countryDAO.getCountries();
         model.addAttribute("countries", countries);
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
    // System.out.println("flash slag::::::"+newUser.getf);
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }
   
 
   
 
}