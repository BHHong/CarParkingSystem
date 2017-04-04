package com.myproject.CarParkingBaySystem.model;

import java.util.Date;

public class ParkingTicket {

	private long ticket;
	private String licensePlate;
	private String vehicleType;
	private Date entryTime;
	private Date paidTime;
	
	public ParkingTicket(long ticket, String licensePlate, String vehicleType) {
		this.ticket = ticket;
		this.licensePlate = licensePlate;
		this.vehicleType = vehicleType;
		this.entryTime = new Date();
		this.paidTime = null;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public long getTicket() {
		return ticket;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	@Override
	public String toString() {
		return "ParkingTicket [ticket=" + ticket + ", licensePlate=" + licensePlate + ", vehicleType=" + vehicleType
				+ ", entryTime=" + entryTime + ", paidTime=" + paidTime + "]";
	}
}