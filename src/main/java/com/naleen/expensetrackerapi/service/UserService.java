package com.naleen.expensetrackerapi.service;

import com.naleen.expensetrackerapi.model.User;
import com.naleen.expensetrackerapi.model.UserModel;

public interface UserService {
	
	public User createUser(UserModel user);
	
	public User readUser();
	
	public User updateUser(UserModel user);
	
	public void deleteUser();
	
	public User getLoggedInUser();
	
}
