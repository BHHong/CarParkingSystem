package com.myproject.CarParkingBaySystem.model;

import java.util.Date;

public class ParkingToken {

	private int tokenNumber;
	private Date entryTime;
	private boolean paid;

	public ParkingToken(int tokenNumber) {
		this.tokenNumber = tokenNumber;
		this.entryTime = new Date();
		this.paid = false;
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Override
	public String toString() {
		return "ParkingToken [tokenNumber=" + tokenNumber + ", entryTime=" + entryTime + ", paid=" + paid + "]";
	}


	
}
