package com.myproject.CarParkingBaySystem.view;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// multiple gate entry, each gate check in 10 cars.
		new Simulation().multipleEntryGate(100, 11);

	}

}
