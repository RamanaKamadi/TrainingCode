package com.login.config;


import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
//    @Bean
//    public String passwordEncoder(String str) {
//       // BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    	  byte[] encoded = Base64.encodeBase64(str.getBytes());  
//        
//        
//        return new String(encoded);
//    }
//    @Bean
//    public String passwordDencoder(String str) {
//      
//    	 byte[] decoded =  Base64.decodeBase64(str.getBytes()); 
//        
//        
//        return new String(decoded);
//    }
//    
//    @Bean
//    public Password passwordDencoder() {
//        BCryptPassword bCryptPasswordEncoder = new 
//        return bCryptPasswordEncoder;
//    }
 
 
    // In this example we do not use Security.
    // Override this method with empty code
    // to disable the default Spring Boot security.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Empty code!
    }
}