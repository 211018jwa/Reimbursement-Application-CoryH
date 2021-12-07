package com.revature.service;

import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UserDAO;
import com.revature.model.User;

public class UserService {

	private UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public User getUserByUsernameAndPassword(String username, String password)
			throws SQLException, FailedLoginException {

		User user = this.userDAO.getUserByUsernameAndPassword(username, password);

		if (user == null) {
			throw new FailedLoginException("Incorrect username and password");
		}

		return user;

	}

}
