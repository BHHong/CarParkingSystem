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
		ExecutorService gates = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (int i = 0; i < gate; i++) {
			gates.submit(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10; j++){
						pso.entryGate();
					}
				}
			});
		}
		gates.shutdown();
		gates.awaitTermination(1, TimeUnit.MILLISECONDS);
	}
	
	public void stressTestMultipleEntryGate(int parkingStructureSpaces, int gate){
		
	}
}
