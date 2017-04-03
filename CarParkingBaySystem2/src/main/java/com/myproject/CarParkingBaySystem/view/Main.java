package com.myproject.CarParkingBaySystem.view;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// Step 1: Total spaces of 100 and 2 entry gates, each gate check in 10 cars.
		// Step 2: Some of the 20 cars pay and exit
		// Step 3: If car park has space, goto Step 1.
		new Simulation().multipleEntryGate(100, 2);
	}

}
