package com.revature.service;

import com.revature.exceptions.UnauthorizedException;
import com.revature.model.User;

// Authentication is different than Authorization 
// Authentication is about providing credentials to identify who you are
// Authorization is about checking whether you have permissions to access a particular thing
public class AuthorizationService {

	public void authorizeRegularAndManager(User user) throws UnauthorizedException {
		// If the user is not either a regular role or manager role
		if (user == null || !(user.getUserRole().equals("user") || user.getUserRole().equals("manager"))) {
			throw new UnauthorizedException("You must have a regular or manager to access this resource");

		}
	}
	
	public void authorizeManger(User user) throws UnauthorizedException {
		if (user == null || !user.getUserRole().equals("manager")) {
			throw new UnauthorizedException("You must have a manager role to access this resource");
		}
	}

}
