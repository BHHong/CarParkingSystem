package com.myproject.CarParkingBaySystem.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.myproject.CarParkingBaySystem.controller.ParkingStructure;
import com.myproject.CarParkingBaySystem.controller.ParkingStructureOffice;

public class Simulation {

	private ParkingStructureOffice pso;

	public void multipleEntryGate(int parkingStructureSpaces, int gate) throws InterruptedException {
		pso = new ParkingStructure(parkingStructureSpaces);
		int repeat = 0;
		int checkInPerGate = 10;
		
		while (pso.hasSpot()){
			ExecutorService gates = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			// entry
			for (int i = 0; i < gate; i++) {
				gates.submit(new Runnable() {
					@Override
					public void run() {
						for (int j = 0; j < checkInPerGate; j++) {
							pso.entryGate();
						}
					}
				});
			}
			gates.shutdown();
			gates.awaitTermination(10, TimeUnit.SECONDS);
			// pay and exit
			for (Integer k : pso.getOccupiedInfo()) {
				if(k > repeat*checkInPerGate*gate && k < (repeat+1)*checkInPerGate*gate-repeat){
					pso.pay(k, 200);
					pso.exitGate(k);
				}
			}
			repeat++;
		} 
		System.out.println("Test completed.");
	}
}
