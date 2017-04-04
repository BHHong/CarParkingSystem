package com.myproject.CarParkingBaySystem.exception;

import com.myproject.CarParkingBaySystem.model.ParkingTicket;
import com.myproject.CarParkingBaySystem.model.Vehicle;

public class ThrowsCustomException {

	public void Unpaid(Vehicle v) {
		try {
			throw new UnpaidException(Thread.currentThread().getName() + " " + v + " Please pay before exiting.");
		} catch (UnpaidException e) {
			System.err.println(e.getMessage());
		}
	}

	public void NoSpot(Vehicle v) {
		try {
			throw new NoSpotException(Thread.currentThread().getName() + " Sorry. No more space for "
					+ v.getClass().getSimpleName().toLowerCase() + " now.");
		} catch (NoSpotException e) {
			System.err.println(e.getMessage());
		}
	}

	public void InsufficientFund(ParkingTicket ticket) {
		try {
			throw new InsufficientFundException(
					"Ticket #" + ticket.getTicket() + " Sorry, your payment is insufficient.");
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
			throw new AlreadyPaidException(Thread.currentThread().getName()+ " Ticket #" + parkingTicket.getTicket() + " " + parkingTicket.getVehicleType()
					+ " [License Plate=" + parkingTicket.getLicensePlate() + "] already paid.");
		} catch (AlreadyPaidException e) {
			System.err.println(e.getMessage());
		}
	}

	public void ExitTimeOut(ParkingTicket parkingTicket) {
		try {
			throw new ExitTimeOutException(Thread.currentThread().getName()+ " Ticket #" + parkingTicket.getTicket()
					+ "You have exceeded 15 mins allowance. Please proceed to help desk.");
		} catch (ExitTimeOutException e) {
			System.err.println(e.getMessage());
		}
	}
}
