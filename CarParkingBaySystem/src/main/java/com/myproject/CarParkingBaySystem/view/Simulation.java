package com.myproject.CarParkingBaySystem.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.myproject.CarParkingBaySystem.controller.ParkingStructure;
import com.myproject.CarParkingBaySystem.controller.ParkingStructureOffice;
import com.myproject.CarParkingBaySystem.model.Car;
import com.myproject.CarParkingBaySystem.model.Motorcycle;

public class Simulation {

	// create parking structure (total motorcycle spaces, motorcycle hourly
	// rate, total car spaces, car hourly rate)
	private ParkingStructureOffice pso = new ParkingStructure(10, 1.11, 15, 2.22);


	public void singleGate(int trial) throws InterruptedException {
		// create license plates for cars and motorcycles
		List<String> carLicensePlates = new ArrayList<>();
		List<String> motorcycleLicensePlates = new ArrayList<>();
		Random r;
		String newLicensePlate;
		for (int i = 1; i <= trial; i++) {
			r = new Random();
			newLicensePlate = licensePlateGenerator();
			int draw = r.nextInt(10);
			if (draw >= 0 && draw <= 2) {
				System.out.println("\nTrial " + i + ": a new car is trying to enter.");
				if (pso.findSpot(new Car(newLicensePlate))) {
					carLicensePlates.add(newLicensePlate);
				}
			} else if (draw >= 3 && draw <= 5) {
				System.out.println("\nTrial " + i + ": a new motorcycle is trying to enter.");
				if (pso.findSpot(new Motorcycle(newLicensePlate))) {
					motorcycleLicensePlates.add(newLicensePlate);
				}
			} else if (draw == 6) {
				System.out.println("\nTrial " + i + ": a car is paying at machine, if any.");
				if (!carLicensePlates.isEmpty()) {
					for (int j = 0; j < carLicensePlates.size(); j++) {
						if (pso.pay(carLicensePlates.get(j), 20)) {
							break;
						}
					}
				}
			} else if (draw == 7) {
				System.out.println("\nTrial " + i + ": a motorcycle is paying at machine, if any.");
				if (!motorcycleLicensePlates.isEmpty()) {
					for (int j = 0; j < motorcycleLicensePlates.size(); j++) {
						if (pso.pay(motorcycleLicensePlates.get(j), 20)) {
							break;
						}
					}
				}
			} else if (draw == 8) {
				System.out.println("\nTrial " + i + ": a car is leaving, if any.");
				if (!carLicensePlates.isEmpty()) {
					if (pso.freeSpot(new Car(carLicensePlates.get(0)))) {
						carLicensePlates.remove(0);
					}
				}
			} else if (draw == 9) {
				System.out.println("\nTrial " + i + ": a motorcycle is leaving, if any.");
				if (!motorcycleLicensePlates.isEmpty()) {
					if (pso.freeSpot(new Motorcycle(motorcycleLicensePlates.get(0)))) {
						motorcycleLicensePlates.remove(0);
					}
				}
			}
			Thread.sleep(100);
		}
	}

	private StringBuilder sb;

	public String licensePlateGenerator() {
		sb = new StringBuilder();
		Random r = new Random();
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 2; i++) {
			sb.append(alpha.charAt(r.nextInt(alpha.length())));
		}
		sb.append(r.nextInt(100));
		for (int i = 0; i < 3; i++) {
			sb.append(String.valueOf(alpha.charAt(r.nextInt(alpha.length()))));
		}
		return sb.toString();
	}
}

