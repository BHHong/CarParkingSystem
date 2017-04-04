package com.myproject.CarParkingBaySystem.model;

public class Car extends Vehicle {

	public Car(String licensePlate) {
		super(licensePlate);
	}

	@Override
	public String toString() {
		return "Car [License Plate=" + super.getLicensePlate() + "]";
	}

}
