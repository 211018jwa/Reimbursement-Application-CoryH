package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.User;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {
	// Whenever you work on an endpoint, you should ask yourself what information do
	// I need to send to the server?
	// For logging in, we need to send
	// 1. Username
	// 2. Password
	// Where should this information be contained inside of the HTTP request
	// 1. Request body (JSON)
	// 2. Path parameters
	// 3. Query parameters
	// 4. Form-data
// How do I receiver this information inside of the my endpoint "handler"?
	// If it's the JSON in the request body -> ctx.bodyAsClass
	// If it's a path parameter -> ctx.pathParam(<param name>)
	// If it's a query parameter -> ctx.queryParam(<query param name>)
	// If it's form data -> ctx.formParam(<key name>)

	private UserService userService;

	public AuthenticationController() {
		this.userService = new UserService();

	}
	// When it comes to logging in, if we logged in successfully, we should create
	// an HttpSession
	// This will essentially automatically find the client to any subsequent
	// requests. That is how we know who is logged in or not.

	private Handler login = (ctx) -> {
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);

		User user = this.userService.getUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

		HttpServletRequest req = ctx.req;

		HttpSession session = req.getSession(); // this will create a new Session if one does not already exist
		session.setAttribute("currentuser", user);

		ctx.json(user);
		
	};
	
	

	private Handler logout = (ctx) -> {
		HttpServletRequest req = ctx.req;

		req.getSession().invalidate();
		
		
	};

	private Handler checkIfLoggedIn = (ctx) -> {
		HttpSession session = ctx.req.getSession();

		// Check if session.getAttribute("currentuser"); is null or not
		if (!(session.getAttribute("currentuser") == null)) {
			ctx.json(session.getAttribute("currentuser"));
			ctx.status(200);
		} else {
			ctx.json(new MessageDTO("User is not logged in"));
			ctx.status(401);
			
			
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/login", login);
		app.post("/logout", logout);
		app.get("/checkloginstatus", checkIfLoggedIn);

	}

}
