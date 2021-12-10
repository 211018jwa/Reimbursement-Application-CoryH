package com.revature.app;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionMapper;
import com.revature.controller.ReimbursementController;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Application {

	public static void main(String[] args) {

		Javalin app = Javalin.create((config) -> {
			config.enableCorsForAllOrigins();

			config.addStaticFiles("static", Location.CLASSPATH);
		});

		mapControllers(app, new AuthenticationController(), new ReimbursementController());
		
		ExceptionMapper mapper = new ExceptionMapper();
		mapper.mapExceptions(app);

		app.start(8080);

		
	}

	public static void mapControllers(Javalin app, Controller... controllers) {
		for (int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndpoints(app);
		}
	}

}
