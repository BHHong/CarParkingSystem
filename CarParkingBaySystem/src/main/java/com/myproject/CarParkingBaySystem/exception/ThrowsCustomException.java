package com.myproject.CarParkingBaySystem.exception;

import com.myproject.CarParkingBaySystem.model.ParkingTicket;
import com.myproject.CarParkingBaySystem.model.Vehicle;

public class ThrowsCustomException {

	public void Unpaid(Vehicle v) {
		try {
			throw new UnpaidException("Please pay before exiting." + v);
		} catch (UnpaidException e) {
			System.err.println(e.getMessage());
		}
	}

	public void NoSpot(Vehicle v) {
		try {
			throw new NoSpotException("Sorry. No more space for " + v.getClass().getSimpleName().toLowerCase() + " now.");
		} catch (NoSpotException e) {
			System.err.println(e.getMessage());
		}

	}

	public void InsufficientFund(ParkingTicket ticket) {
		try {
			throw new InsufficientFundException("Sorry. Your payment is insufficient. #" + ticket.getToken());
		} catch (InsufficientFundException e) {
			System.err.println(e.getMessage());
		}

	}

	public void Duplicate(Vehicle v) {
		try {
			throw new DuplicateException("Error. Same vehicle registered in car park. Please proceed to help desk.");
		} catch (DuplicateException e) {
			System.err.println(e.getMessage());
		}
	}

	public void AlreadyPaid(ParkingTicket parkingTicket) {
		try {
			throw new AlreadyPaidException("Already paid. Please proceed to exit gate. #" + parkingTicket.getToken());
		} catch (AlreadyPaidException e) {
			System.err.println(e.getMessage());
		}
	}
}
