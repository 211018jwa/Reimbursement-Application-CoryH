package com.revature.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.model.User;
import com.revature.dao.ReimbursementDAO;
import com.revature.exceptions.ReimbursementAlreadyUpdatedException;
import com.revature.exceptions.ReimbursementNotFoundException;
import com.revature.model.Reimbursement;

public class ReimbursementService {

	private ReimbursementDAO reimbursementDao;

	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}

	public ReimbursementService(ReimbursementDAO reimbursementDAO) {
		this.reimbursementDao = reimbursementDAO;

	}

	public List<Reimbursement> getReimbursements(User currentlyLoggedInUser) throws SQLException {
		List<Reimbursement> reimbursements = null;

		if (currentlyLoggedInUser.getUserRole().equals("Financial Manager")) {
			reimbursements = this.reimbursementDao.getAllReimbursements();
		} else if (currentlyLoggedInUser.getUserRole().equals("Employee")) {
			reimbursements = this.reimbursementDao.getReimbursementsByEmployee(currentlyLoggedInUser.getId());
		}

		return reimbursements;
	}

	// We only want to be able to change the status once
	// Once we have already changed the status, it is permanent, you can't change it
	// from there
	// 1. Check if the assignment already has a grade
	// - if it does, throw an AlreadyUpdatedException
	// 2. Otherwise if doesn't have an updated status proceed onward to change
	// status

	public Reimbursement changeStatus(User currentlyLoggedInUser, String reimbId, String status)
			throws SQLException, ReimbursementNotFoundException, ReimbursementAlreadyUpdatedException, NumberFormatException {
		
		int id = Integer.parseInt(reimbId);
		Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

		try {
			
			if (reimbursement == null) {
				throw new ReimbursementNotFoundException("Reimbursement with that id " + reimbId + " was not found");

			}

			if (reimbursement.getResolver() == 0) {
				this.reimbursementDao.changeStatus(id, status, reimbursement, currentlyLoggedInUser.getId());
			} else {
				throw new ReimbursementAlreadyUpdatedException(
						"Reimbursement Status has already been updated, therefore can't go back and edit");
			}
			
			return this.reimbursementDao.changeStatus(id, status, reimbursement, currentlyLoggedInUser.getId());

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}

	}
	// Business Logic
	// We want to check if the MIMETYPE is either image
	public Reimbursement addReimbursement(User currentlyLoggedInUser, String reimbAmount, String reimbDescription, String reimbType, InputStream content, String mimeType) throws SQLException {
		Double rAmount = Double.parseDouble(reimbAmount);
		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/gif");
		allowedFileTypes.add("image/png");
		
		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException("When adding an Receipt, only PNG, JPEG, or GIF are allowed");
			
		}
		// Author, reimbursement name, file content(bytes, 0s and 1s)
		int authorId = currentlyLoggedInUser.getId(); // Whoever is logged in, will be the actual author of the reimbursement
		
		Reimbursement addedReimbursement = this.reimbursementDao.addReimbursement(rAmount, authorId, content, reimbDescription, reimbType);
			
		return addedReimbursement;
	}

}
