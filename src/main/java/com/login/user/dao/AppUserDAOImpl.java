package com.login.user.dao;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.login.user.model.AppUser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class AppUserDAOImpl implements AppUserDAO {

	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public AppUserDAOImpl(MongoTemplate mongoTemplate) {
		
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public AppUser findAppUserByEmail(String email) {
		
		return null;
	}

	@Override
	public AppUser findAppUserByUserName(String userName) {
		
		return null;
	}

	@Override
	public AppUser saveAppUser(AppUser user) {
		 mongoTemplate.save(user);
		return user;
	}

	@Override
	public void updateMultipleUsers() {
		
		
	}

	@Override
	public AppUser updateOneUser(AppUser user) {
		
		
		System.out.println("updateuser method::::::"+user.getUserName());
		 String[] str= {"encrytedPassword","userId"};
		
		 Query query = new Query(Criteria.where("userName").is(user.getUserName()));
		 Document doc = new Document(); // org.bson.Document
	     mongoTemplate.getConverter().write(user, doc);
	     
	     Update update = new Update();
	     
	     for (String key : doc.keySet()) {
	         Object value = doc.get(key);
	         if(value!=null){
	             update.set(key, value);
	         }
	     }
	    //Update.fromDocument(doc,str);   //it will update null value if filed value null....      	        	        
		 mongoTemplate.updateMulti(query, update, AppUser.class);
		 
		 
		
	        
		return user;
	}

	@Override
	public void deleteUser(AppUser user) {
		
		
	}

	@Override
	public List<AppUser> getAllUsers() {
		
	 return mongoTemplate.findAll(AppUser.class);
	}
	
	
	
}
