package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.ChangeStatusDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;


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
	
	private Handler addReimbursement = (ctx) -> {
		// Protect Endpoint
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployee(currentlyLoggedInUser);
 		
		String reimbAmount = ctx.formParam("reimb_amount");
		String reimbDescription = ctx.formParam("reimb_description");
		String reimbType = ctx.formParam("reimb_type");
		
		/*
		 * Extracting the file from the HTTP request
		 */
		
		UploadedFile reimbReceipt = ctx.uploadedFile("reimb_receipt");
		InputStream content = reimbReceipt.getContent();
		
		Tika tika = new Tika();
		
		// We want to disallow users from uploading files that are not jpeg, gif, or png
		// So, in the controller layer figure out the MIME type of the file
		// jpeg = image/jpeg
		// gif = image/gif
		// png = image/png
		
		String mimeType = tika.detect(content);
		
		// Service layer invocation
		Reimbursement addedReimbursement = this.reimbursementService.addReimbursement(currentlyLoggedInUser, reimbAmount, reimbDescription, reimbType, content, mimeType);
		ctx.json(addedReimbursement);
		ctx.status(201);
		
		
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/reimbursements", getReimbursements);
		app.patch("/reimbursements/{reimb_id}/updateReimbursement", changeStatus);
		app.post("/reimbursements", addReimbursement);
	}

}
