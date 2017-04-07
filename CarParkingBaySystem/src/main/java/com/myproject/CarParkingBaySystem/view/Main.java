package com.myproject.CarParkingBaySystem.view;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// car park with 20 motorcycle spaces and 100 car spaces
		// will throw error exception if either motorcycle/car exceeds spaces of car park. 
		long startTime = System.nanoTime();
		int rerun = 5; // reset database and re-run; total ticket number retained.
		for (int i = 0; i < rerun; i++) {
			new Simulation().multiGate(4,1000); // input number of gates, trials per gate. 
		}
		long endTime = System.nanoTime();
		System.out.println("\nTest completed in " + (endTime-startTime)/1000000L +" ms");
		
	}

}
