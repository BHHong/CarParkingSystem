package com.myproject.CarParkingBaySystem.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.Car;
import com.myproject.CarParkingBaySystem.model.Motorcycle;
import com.myproject.CarParkingBaySystem.model.ParkingTicket;
import com.myproject.CarParkingBaySystem.model.Vehicle;

public class ParkingStructure implements ParkingStructureOffice {

	private static Map<Vehicle, ParkingTicket> records = new ConcurrentHashMap<>();
	private static long ticket;

	private final int totalMotorcycleSpaces;
	private final int totalCarSpaces;
	private PaymentSystem paymentSystem;

	public ParkingStructure(int totalMotorcycleSpaces, double motorcycleHourlyRate, int totalCarSpaces,
			double carHourlyRate) {
		this.ticket = 0;
		this.totalMotorcycleSpaces = totalMotorcycleSpaces;
		this.totalCarSpaces = totalCarSpaces;
		this.paymentSystem = new PaymentSystem(motorcycleHourlyRate, carHourlyRate);
	}

	@Override
	public boolean hasSpot(Vehicle v) {
		int totalSpaces = v.getClass().getSimpleName().equals("Car") ? totalCarSpaces
				: v.getClass().getSimpleName().equals("Motorcycle") ? totalMotorcycleSpaces : 0;
		if (getOccupancies(v.getClass().getSimpleName()) < totalSpaces) {
			return true;
		}
		return false;
	}

	public int getOccupancies(String vehicleType) {
		int totalFound = 0;
		for (Vehicle v : records.keySet()) {
			if (v.getClass().getSimpleName().equals(vehicleType)) {
				totalFound++;
			}
		}
		return totalFound;
	}

	@Override
	public boolean findSpot(Vehicle v) {
		if (hasSpot(v)) {
			if (records.putIfAbsent(v,new ParkingTicket(++ticket, v.getLicensePlate(), v.getClass().getSimpleName())) == null) {
				System.out.println(Thread.currentThread().getName() + " Ticket #" + ticket + " issued to " + v);
				getFreeSpacesInfo();
				return true;
			} else {
				new ThrowsCustomException().Duplicate(v);
			}
		} else {
			new ThrowsCustomException().NoSpot(v);
		}
		return false;
	}

	@Override
	public boolean pay(String licensePlate, double payment) {
		ParkingTicket parkingTicket = records.get(new Car(licensePlate)) != null ? records.get(new Car(licensePlate)) : records.get(new Motorcycle(licensePlate));
		if (paymentSystem.processPayment(parkingTicket, payment)) {
			System.out.println(Thread.currentThread().getName() +" Ticket #" + parkingTicket.getToken() + " : Payment Successful." );
			return true;
		}
		return false;
	}

	@Override
	public boolean freeSpot(Vehicle v) {
		if (records.get(v).getPaidTime() != null) {
			System.out.println(Thread.currentThread().getName() + " Ticket #" + records.get(v).getToken() + " collected.");
			records.remove(v);
			getFreeSpacesInfo();
			return true;
		} else {
			new ThrowsCustomException().Unpaid(v);
		}
		return false;
	}

	public void getFreeSpacesInfo() {
		int totalMotorcycleFound = 0;
		int totalCarFound = 0;
		for (Vehicle v : records.keySet()) {
			if (v.getClass().getSimpleName().equals("Motorcycle")) {
				totalMotorcycleFound++;
			} else if (v.getClass().getSimpleName().equals("Car")) {
				totalCarFound++;
			}
		}
		System.out.println(Thread.currentThread().getName()+" Free spaces: Motorcycle [" + (totalMotorcycleSpaces - totalMotorcycleFound) + "/"
				+ totalMotorcycleSpaces + "], Car [" + (totalCarSpaces - totalCarFound) + "/" + totalCarSpaces + "]");
	}

}
