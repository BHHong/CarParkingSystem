package com.myproject.CarParkingBaySystem.exception;

public class ThrowsCustomException {

	public void Unpaid(Integer counter) {
		try {
			throw new UnpaidException(Thread.currentThread().getName() + " Token #" + counter + " : Please pay before exit.");
		} catch (UnpaidException e) {
			System.err.println(e.getMessage());
		}
	}

	public void NoSpot() {
		try {
			throw new NoSpotException(Thread.currentThread().getName() + " Sorry. Car park is now full. Please wait.");
		} catch (NoSpotException e) {
			System.err.println(e.getMessage());
		}

	}

	public void InsufficientFund(Integer counter) {
		try {
		throw new InsufficientFundException(Thread.currentThread().getName() + " Token #" + counter + " payment failed: Insufficient fund.");
		} catch (InsufficientFundException e) {
			System.err.println(e.getMessage());
		}

	}
}
