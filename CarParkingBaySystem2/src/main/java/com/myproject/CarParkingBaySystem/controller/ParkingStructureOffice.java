package com.myproject.CarParkingBaySystem.controller;

public interface ParkingStructureOffice {

	public void entryGate();

	public void exitGate(Integer counter);

	public void pay(Integer counter, double payment);
	
	public void capacityInfo();
}
