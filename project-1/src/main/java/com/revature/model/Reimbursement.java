package com.revature.model;


import java.util.Date;
import java.util.Objects;

public class Reimbursement {
	
	private int reimbId;
	private double reimbAmount;
	private Date submitted;
	private String status;
	private String resolved;
	private String type;
	private String description;
	private int author;
	private int resolver;
	
	
	
	public Reimbursement() {
		super();

	}


	public Reimbursement(int reimbId, double reimbAmount, Date generatedTime, String status, String resolved, String type,
			String description, int author, int resolver) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.submitted = generatedTime;
		this.status = status;
		this.resolved = resolved;
		this.type = type;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
	
	}


	public int getReimbId() {
		return reimbId;
	}


	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}


	public double getReimbAmount() {
		return reimbAmount;
	}


	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}


	public Date getSubmitted() {
		return submitted;
	}


	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getResolved() {
		return resolved;
	}


	public void setResolved(String resolved) {
		this.resolved = resolved;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getAuthor() {
		return author;
	}


	public void setAuthor(int author) {
		this.author = author;
	}


	public int getResolver() {
		return resolver;
	}


	public void setResolver(int resolver) {
		this.resolver = resolver;
	}


	@Override
	public int hashCode() {
		return Objects.hash(author, description, reimbAmount, reimbId, resolved, resolver, status, submitted, type);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return author == other.author && Objects.equals(description, other.description)
				&& Double.doubleToLongBits(reimbAmount) == Double.doubleToLongBits(other.reimbAmount)
				&& reimbId == other.reimbId && Objects.equals(resolved, other.resolved) && resolver == other.resolver
				&& Objects.equals(status, other.status) && Objects.equals(submitted, other.submitted)
				&& Objects.equals(type, other.type);
	}


	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", submitted=" + submitted
				+ ", status=" + status + ", resolved=" + resolved + ", type=" + type + ", description=" + description
				+ ", author=" + author + ", resolver=" + resolver + "]";
	}

	
	

}
