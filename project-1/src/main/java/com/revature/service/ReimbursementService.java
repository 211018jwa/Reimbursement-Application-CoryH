package com.revature.service;

import java.sql.SQLException;
import java.util.List;
import com.revature.model.User;
import com.revature.dao.ReimbursementDAO;
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
			reimbursements	= this.reimbursementDao.getAllReimbursements();
		}else if (currentlyLoggedInUser.getUserRole().equals("Employee")) {
			reimbursements = this.reimbursementDao.getReimbursementsByEmployee(currentlyLoggedInUser.getId());
		}
		
		return reimbursements;
	}
	

}
