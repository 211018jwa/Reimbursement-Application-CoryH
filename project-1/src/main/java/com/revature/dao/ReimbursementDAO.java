package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.utility.JDBCUtility;

public class ReimbursementDAO {

	public List<Reimbursement> getAllReimbursements() throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT * FROM project1.ers_reimbursement";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				String submitted = rs.getString("reimb_submitted");
				String type = rs.getString("reimb_type");
				String status = rs.getString("reimb_status");
				String resolved = rs.getString("reimb_resolved");
				String description = rs.getString("reimb_description");
				int reimbAuthor = rs.getInt("reimb_author");
				int reimbResolver = rs.getInt("reimb_resolver");

				Reimbursement reimbursement = new Reimbursement(reimbId, amount, submitted, type, status, resolved,
						description, reimbAuthor, reimbResolver);

				reimbursements.add(reimbursement);

			}

			return reimbursements;
		}
	}

	public List<Reimbursement> getReimbursementsByEmployee(int userId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();
			
			String sql = "SELECT * FROM project1.ers_reimbursement WHERE reimb_author = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				String submitted = rs.getString("reimb_submitted");
				String type = rs.getString("reimb_type");
				String status = rs.getString("reimb_status");
				String resolved = rs.getString("reimb_resolved");
				String description = rs.getString("reimb_description");
				int reimbAuthor = rs.getInt("reimb_author");
				int reimbResolver = rs.getInt("reimb_resolver");

				Reimbursement reimbursement = new Reimbursement(reimbId, amount, submitted, type, status, resolved,
						description, reimbAuthor, reimbResolver);

				reimbursements.add(reimbursement);

			}

			return reimbursements;

		}
	}
}
