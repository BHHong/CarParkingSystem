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
	private ParkingStructureOffice pso = new ParkingStructure(50, 1, 100, 2);
	private List<String> carLicensePlates = new ArrayList<>();
	private List<String> motorcycleLicensePlates = new ArrayList<>();
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

	public void run() {
		// create license plates for cars and motorcycles
		Random r;
		String newLicensePlate;
		for (int i = 1; i <= 1000; i++) {
			r = new Random();
			newLicensePlate = licensePlateGenerator();
			int draw = r.nextInt(4);
			if (draw == 0) {
				System.out.println(i + ": a new car is trying to enter.");
				if (pso.findSpot(new Car(newLicensePlate))) {
					carLicensePlates.add(newLicensePlate);
				}
			} else if (draw == 1) {
				System.out.println(i + ": a new motorcycle is trying to enter.");
				if (pso.findSpot(new Motorcycle(newLicensePlate))) {
					motorcycleLicensePlates.add(newLicensePlate);
				}
			} else if (draw == 2) {
				System.out.println(i + ": a car is leaving if any.");
				if (!carLicensePlates.isEmpty()) {
					pso.pay(carLicensePlates.get(0), 20);
					pso.freeSpot(new Car(carLicensePlates.get(0)));
					carLicensePlates.remove(0);
				}
			} else if (draw == 3) {
				System.out.println(i + ": a motorcycle is leaving if any.");
				if (!motorcycleLicensePlates.isEmpty()) {
					pso.pay(motorcycleLicensePlates.get(0), 20);
					pso.freeSpot(new Motorcycle(motorcycleLicensePlates.get(0)));
					motorcycleLicensePlates.remove(0);
				}
			}
		}
	}
}
