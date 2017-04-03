package com.myproject.CarParkingBaySystem.controller;

import java.util.Date;

import com.myproject.CarParkingBaySystem.model.ParkingToken;

public class PaymentSystem {

	private ParkingToken token;
	private double payment;
	private double hourlyRate;

	public PaymentSystem(ParkingToken token, double payment) {
		this.token = token;
		this.payment = payment;
		this.hourlyRate = 1.00d;
	}

	public double getAmount(double hourlyRate) {
		Date exitTime = new Date();
		int parkedHour = (int) Math.ceil((double) (exitTime.getTime() - token.getEntryTime().getTime()) / 1000 / 60 / 60);
		double amount = parkedHour * hourlyRate;
		//System.out.println("Token #" + token.getTokenNumber() + " Entry Time : " + token.getEntryTime());
		System.out.println("Token #" + token.getTokenNumber() + " Payment Time : " + exitTime);
		System.out.println("Token #" + token.getTokenNumber() + " Parked up to " + parkedHour + " hour(s) : Cost GBP " + amount);
		return amount;
	}

	public boolean isSuccessful() {
		//System.out.println("Token #" + token.getTokenNumber() + " receiving payment GBP " + payment);
		if (payment >= getAmount(hourlyRate)) {
			token.setPaid(true);
			return true;
		}
		return false;
	}

	public ParkingToken getParkingSpace() {
		return token;
	}
}
