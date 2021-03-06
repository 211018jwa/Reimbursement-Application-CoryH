package com.revature.service;

import com.revature.exceptions.UnauthorizedException;
import com.revature.model.User;

// Authentication is different than Authorization 
// Authentication is about providing credentials to identify who you are
// Authorization is about checking whether you have permissions to access a particular thing
public class AuthorizationService {

	public void authorizeEmployeeAndFinanceManager(User user) throws UnauthorizedException {
		// If the user is not either a regular role or manager role
		if (user == null || !(user.getUserRole().equals("Employee") || user.getUserRole().equals("Financial Manager"))) {
			throw new UnauthorizedException("You must have a Employee or Finance Manager to access this resource");

		}
	}
	
	public void authorizeFinanceManger(User user) throws UnauthorizedException {
		if (user == null || !user.getUserRole().equals("Financial Manager")) {
			throw new UnauthorizedException("You must have a Finance Manager role to access this resource");
		}
	}

	public void authorizeEmployee(User currentlyLoggedInUser) throws UnauthorizedException {
		if (currentlyLoggedInUser == null || !currentlyLoggedInUser.getUserRole().equals("Employee")) {
			throw new UnauthorizedException("You must be logged in and have a role of Employee for this resource");
			
		}
	}

}
