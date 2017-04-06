package com.myproject.CarParkingBaySystem.controller;

import java.util.Date;
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
	private int exitTimeOutMinutes = 20;

	public ParkingStructure(int totalMotorcycleSpaces, double motorcycleHourlyRate, int totalCarSpaces,
			double carHourlyRate) {
		this.ticket = 0;
		this.totalMotorcycleSpaces = totalMotorcycleSpaces;
		this.totalCarSpaces = totalCarSpaces;
		this.paymentSystem = new PaymentSystem(motorcycleHourlyRate, carHourlyRate);
	}

	@Override
	public boolean hasSpot(Class<? extends Vehicle> vehicleClass) {
		if (getOccupancies(vehicleClass) < (vehicleClass == Car.class ? totalCarSpaces
				: vehicleClass == Motorcycle.class ? totalMotorcycleSpaces : 0)) {
			return true;
		}
		return false;
	}

	@Override
	public int getOccupancies(Class<? extends Vehicle> vehicleClass) {
		return (int) records.keySet().stream().filter(key -> key.getClass() == vehicleClass).count();
	}

	@Override
	public boolean findSpot(Vehicle v) {
		if (hasSpot(v.getClass())) {
			if (records.putIfAbsent(v,
					new ParkingTicket(++ticket, v.getLicensePlate(), v.getClass().getSimpleName())) == null) {
				System.out.println(Thread.currentThread().getName() 
						+ " Ticket #" + records.get(v).getTicket() + " " + v + " entered.");
				getFreeSpacesInfo();
				return true;
			} else {
				new ThrowsCustomException().Duplicate(v);
			}
		} else {
			getFreeSpacesInfo();
			new ThrowsCustomException().NoSpot(v);
		}
		return false;
	}

	@Override
	public boolean pay(String licensePlate, double payment) {
		ParkingTicket parkingTicket = records.get(new Car(licensePlate)) != null ? 
				records.get(new Car(licensePlate)) : records.get(new Motorcycle(licensePlate));
		if (paymentSystem.processPayment(parkingTicket, payment)) {
			System.out.println(Thread.currentThread().getName() 
					+ " Ticket #" + parkingTicket.getTicket() + " Payment Successful.");
			return true;
		}
		return false;
	}

	@Override
	public boolean freeSpot(Vehicle v) {
		if (records.get(v).getPaidTime() != null) {
			if (new Date().getTime() - records.get(v).getPaidTime().getTime() <= exitTimeOutMinutes * 60 * 1000) {
				System.out.println(Thread.currentThread().getName() 
						+ " Ticket #" + records.get(v).getTicket() + " " + v + " left.");
				records.remove(v);
				getFreeSpacesInfo();
				return true;
			} else {
				new ThrowsCustomException().ExitTimeOut(records.get(v));
			}
		} else {
			new ThrowsCustomException().Unpaid(v);
		}
		return false;
	}

	@Override
	public void getFreeSpacesInfo() {
		System.out.println(Thread.currentThread().getName() + " Free spaces: Motorcycle ["
				+ (totalMotorcycleSpaces - getOccupancies(Motorcycle.class)) + "/" + totalMotorcycleSpaces + "], Car ["
				+ (totalCarSpaces - getOccupancies(Car.class)) + "/" + totalCarSpaces + "]");
	}

}
