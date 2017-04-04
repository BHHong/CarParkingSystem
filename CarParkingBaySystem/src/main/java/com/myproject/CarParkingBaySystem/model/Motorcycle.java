package com.myproject.CarParkingBaySystem.model;

public class Motorcycle extends Vehicle {

	public Motorcycle(String licensePlate) {
		super(licensePlate);
	}

	@Override
	public String toString() {
		return "Motorcycle [License Plate=" + super.getLicensePlate() + "]";
	}

}
