package com.myproject.CarParkingBaySystem.controller;

import com.myproject.CarParkingBaySystem.model.Vehicle;

public interface ParkingStructureOffice {

	public boolean hasSpot(Class<? extends Vehicle> vehicleClass);

	public int getOccupancies(Class<? extends Vehicle> vehicleClass);

	public boolean findSpot(Vehicle v);

	public boolean freeSpot(Vehicle v);

	public boolean pay(String licensePlate, double payment);

	public void getFreeSpacesInfo();
}
