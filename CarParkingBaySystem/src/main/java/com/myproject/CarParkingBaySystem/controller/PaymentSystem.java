package com.myproject.CarParkingBaySystem.controller;

import java.util.Date;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.ParkingTicket;

public class PaymentSystem {

	private final double carHourlyRate;
	private final double motorcycleHourlyRate;

	public PaymentSystem(double motorcycleHourlyRate, double carHourlyRate) {
		this.motorcycleHourlyRate = motorcycleHourlyRate;
		this.carHourlyRate = carHourlyRate;
	}

	public boolean processPayment(ParkingTicket parkingTicket, double payment) {
		if (parkingTicket.getPaidTime() != null) {
			new ThrowsCustomException().AlreadyPaid(parkingTicket);
			return false;
		} else if (payment >= getAmount(parkingTicket)) {
			parkingTicket.setPaidTime(new Date());
			return true;
		}
		return false;
	}

	public double getAmount(ParkingTicket parkingTicket) {
		Date payTime = new Date();
		int parkedHour = (int) Math.ceil((double) (payTime.getTime() - parkingTicket.getEntryTime().getTime()) / 1000 / 60 / 60);
		if (parkedHour == 0) {
			parkedHour = 1;
		}
		double rate = parkingTicket.getVehicleType().equals("Car") ? carHourlyRate : parkingTicket.getVehicleType().equals("Motorcycle") ? motorcycleHourlyRate : 10000;
		double amount = parkedHour * rate;
		System.out.println(Thread.currentThread().getName() + " Ticket #" + parkingTicket.getToken() + " due GBP " + amount);
		return amount;
	}
}
