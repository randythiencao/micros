package com.test.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_state")
public class UserState {

	@EmbeddedId
	private UserStateId userStateId;
	
	@Column(name="CREATED_DT")
	private Timestamp createdDt;

	public UserState(UserStateId userStateId, Timestamp createdDt) {
		super();
		this.userStateId = userStateId;
		this.createdDt = createdDt;
	}

	public UserState() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserStateId getUserStateId() {
		return userStateId;
	}

	public void setUserStateId(UserStateId userStateId) {
		this.userStateId = userStateId;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdDt == null) ? 0 : createdDt.hashCode());
		result = prime * result + ((userStateId == null) ? 0 : userStateId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserState other = (UserState) obj;
		if (createdDt == null) {
			if (other.createdDt != null)
				return false;
		} else if (!createdDt.equals(other.createdDt))
			return false;
		if (userStateId == null) {
			if (other.userStateId != null)
				return false;
		} else if (!userStateId.equals(other.userStateId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserState [userStateId=" + userStateId + ", createdDt=" + createdDt + "]";
	}
	
	
	
}
