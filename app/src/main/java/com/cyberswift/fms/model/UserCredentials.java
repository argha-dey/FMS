package com.cyberswift.fms.model;

public class UserCredentials {

	// Declaring model class variables
	
	int user_id;
    String user_name;
    String password;
    String user_type;
    String user_logged_in_flag;
	
	// constructors
    public UserCredentials() 
    {
    	
    }
    
    public UserCredentials(String user_name, String password) 
    {
        this.user_name = user_name;
        this.password = password;
    }
    
    public UserCredentials(String user_name, String password, String user_type) 
    {
        this.user_name = user_name;
        this.password = password;
        this.user_type = user_type;
    }
    
    public UserCredentials(int user_id,String user_name, String password, String user_type) 
    {
    	this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.user_type = user_type;
    }
    
    public UserCredentials(int user_id,String user_name, String password, String user_type, String user_logged_in_flag) 
    {
    	this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.user_type = user_type;
        this.user_logged_in_flag = user_logged_in_flag;
    }
    
    // setters
    
    // setting user_id
    public void setUserId(int user_id) 
    {
        this.user_id = user_id;
    }
 
    // setting user_name
    public void setUserName(String user_name) 
    {
        this.user_name = user_name;
    }
 
    // setting password
    public void setPassword(String password)
    {
        this.password = password;
    }
     
    // setting password
    public void setUserType(String user_type)
    {
        this.user_type = user_type;
    }
    
    // setting user_logged_in_flag
    public void setUserLoggedInFlag(String user_logged_in_flag)
    {
        this.user_logged_in_flag = user_logged_in_flag;
    }
    
    
 
    // getters
    
    // getting user_id
    public int getUserId() {
        return this.user_id;
    }
 
    // getting user_name
    public String getUserName() {
        return this.user_name;
    }
 
    // getting password
    public String getPassword() {
        return this.password;
    }
    
    // getting user_type
    public String getUserType() {
        return this.user_type;
    }
    // getting user_logged_in_flag
    public String getUserLoggedInFlag() {
        return this.user_logged_in_flag;
    }
    
}
