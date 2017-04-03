package com.myproject.CarParkingBaySystem.controller;

import java.util.List;

public interface ParkingStructureOffice {

	public void entryGate();

	public void exitGate(Integer counter);

	public void pay(Integer counter, double payment);
	
	public int capacityInfo();

	public boolean hasSpot();

	public List<Integer> getOccupiedInfo();
}
