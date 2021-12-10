package com.revature.dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
				Date submitted = rs.getTimestamp("reimb_submitted");
				String type = rs.getString("reimb_type");
				String status = rs.getString("reimb_status");
				String resolved = rs.getString("reimb_resolved");
				String description = rs.getString("reimb_description");
				int reimbAuthor = rs.getInt("reimb_author");
				int reimbResolver = rs.getInt("reimb_resolver");

				Reimbursement reimbursement = new Reimbursement(reimbId, amount, submitted, status, resolved, type,
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
				Date submitted = rs.getTimestamp("reimb_submitted");
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

	public Reimbursement getReimbursementById(int reimbId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM project1.ers_reimbursement WHERE reimb_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimbId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("reimb_id");
				double amount = rs.getDouble("reimb_amount");
				Date submitted = rs.getTimestamp("reimb_submitted");
				String type = rs.getString("reimb_type");
				String status = rs.getString("reimb_status");
				String resolved = rs.getString("reimb_resolved");
				String description = rs.getString("reimb_description");
				int reimbAuthor = rs.getInt("reimb_author");
				int reimbResolver = rs.getInt("reimb_resolver");

				return new Reimbursement(id, amount, submitted, status, resolved, type, description, reimbAuthor,
						reimbResolver);
			} else {
				return null;
			}

		}
	}

	public Reimbursement changeStatus(int id, String status, Reimbursement reimbursement, int reimbResolver)
			throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE project1.ers_reimbursement " + "SET "
					+ "reimb_resolved = NOW(), " + "reimb_status = ?, "
					+ "reimb_resolver = ? " + "WHERE reimb_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, status);
			pstmt.setInt(2, reimbResolver);
			pstmt.setInt(3, id);

			pstmt.executeUpdate();

			Reimbursement updatedReimbursement = getReimbursementById(id);

			return updatedReimbursement;

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());

		}

	}


	public Reimbursement addReimbursement(Double rAmount, int authorId, InputStream content, String reimbDescription, String type) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false);
			String sql = "INSERT INTO project1.ers_reimbursement (reimb_amount, reimb_author, reimb_receipt, reimb_description, reimb_type)"
					+ "VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, rAmount);
			pstmt.setInt(2, authorId);
			pstmt.setBinaryStream(3, content);
			pstmt.setString(4, reimbDescription);
			pstmt.setString(5, type);
			
			int numberOfInsertedRecords = pstmt.executeUpdate();
			
			if (numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occurred when adding reimbursement");
				
			}
			
			ResultSet rs = pstmt.getGeneratedKeys();
			
			rs.next();
			
			int generatedId = rs.getInt(1);
			Date generatedTime = rs.getTime(3);
			
			con.commit();
			
			return new Reimbursement(generatedId, rAmount, generatedTime, "PENDING", null, type, reimbDescription, authorId, 0);
			
			
		}
	}
			
			

}
