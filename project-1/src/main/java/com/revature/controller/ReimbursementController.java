package com.revature.controller;

import java.util.List;

import com.revature.dto.ChangeStatusDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller {

	private AuthorizationService authService;
	private ReimbursementService reimbursementService;

	public ReimbursementController() {
		this.authService = new AuthorizationService();
		this.reimbursementService = new ReimbursementService();
	}

	private Handler getReimbursements = (ctx) -> {
		// guard this end point
		// roles permitted: Finance Manager, Employee
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		// If the above this.authService.authorizeAssociateAndTrainer(...) method did
		// not throw
		// an exception, that means our program will continue to proceed to the below
		// functionality
		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursements(currentlyLoggedInUser);
		
		ctx.json(reimbursements);

	};
	// Only Financial manager can access this end point
	private Handler changeStatus = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManger(currentlyLoggedInUser);
		
		String reimbId = ctx.pathParam("reimb_id");
		ChangeStatusDTO dto = ctx.bodyAsClass(ChangeStatusDTO.class);
		
		Reimbursement changedStatus = this.reimbursementService.changeStatus(currentlyLoggedInUser, reimbId, dto.getStatus());
		ctx.json(changedStatus);

		
		
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/reimbursements", getReimbursements);
		app.patch("/reimbursements/{reimb_id}/updateReimbursement", changeStatus);
	}

}
