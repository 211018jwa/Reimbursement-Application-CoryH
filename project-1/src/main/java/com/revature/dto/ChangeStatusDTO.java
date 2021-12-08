package com.revature.dto;

import java.util.Objects;

public class ChangeStatusDTO {
	
	private String status;

	public ChangeStatusDTO() {
		super();
	}

	public ChangeStatusDTO(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChangeStatusDTO other = (ChangeStatusDTO) obj;
		return Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "ChangeStatusDTO [status=" + status + "]";
	}
	
	
	
	

}
