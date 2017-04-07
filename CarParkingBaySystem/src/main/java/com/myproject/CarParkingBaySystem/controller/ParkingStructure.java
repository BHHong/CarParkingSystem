package com.myproject.CarParkingBaySystem.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.Car;
import com.myproject.CarParkingBaySystem.model.Motorcycle;
import com.myproject.CarParkingBaySystem.model.ParkingTicket;
import com.myproject.CarParkingBaySystem.model.Vehicle;

public class ParkingStructure implements ParkingStructureOffice {

	private static Map<Vehicle, ParkingTicket> records = new HashMap<>();
	private static long ticket = 0;

	private PaymentSystem paymentSystem;
	private final int totalMotorcycleSpaces;
	private final int totalCarSpaces;
	private int exitTimeOutMinutes = 20;

	public ParkingStructure(int totalMotorcycleSpaces, double motorcycleHourlyRate, int totalCarSpaces,
			double carHourlyRate) {
		this.totalMotorcycleSpaces = totalMotorcycleSpaces;
		this.totalCarSpaces = totalCarSpaces;
		this.paymentSystem = new PaymentSystem(motorcycleHourlyRate, carHourlyRate);
	}

	@Override
	public boolean hasSpot(Class<? extends Vehicle> vehicleClass) {
		if (getOccupancies(vehicleClass) < (vehicleClass.equals(Car.class) ? totalCarSpaces
				: vehicleClass.equals(Motorcycle.class) ? totalMotorcycleSpaces : 0)) {
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
		synchronized (records) {
			if (hasSpot(v.getClass())) {
				records.put(v,new ParkingTicket(++ticket, v.getLicensePlate(), v.getClass().getSimpleName()));
				System.out.println(Thread.currentThread().getName() + " Ticket #" + records.get(v).getTicket() + " " + v + " entered."); 
				getFreeSpacesInfo();
				return true;
			} else {
				getFreeSpacesInfo();
				new ThrowsCustomException().NoSpot(v);
			}
			return false;
		}
	}

	@Override
	public boolean pay(String licensePlate, double payment) {
		ParkingTicket parkingTicket = records.get(new Car(licensePlate)) != null ? records.get(new Car(licensePlate))
				: records.get(new Motorcycle(licensePlate));
		if (paymentSystem.processPayment(parkingTicket, payment)) {
			System.out.println(Thread.currentThread().getName() + " Ticket #" + parkingTicket.getTicket()
					+ " Payment Successful.");
			return true;
		}
		return false;
	}

	@Override
	public boolean freeSpot(Vehicle v) {
		if (records.get(v).getPaidTime() != null) {
			if (new Date().getTime() - records.get(v).getPaidTime().getTime() <= exitTimeOutMinutes * 60 * 1000) {
				System.out.println(Thread.currentThread().getName() + " Ticket #" + records.get(v).getTicket() + " " + v
						+ " left.");
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
		int mFreeSpace = (totalMotorcycleSpaces - getOccupancies(Motorcycle.class));
		int cFreeSpace = (totalCarSpaces - getOccupancies(Car.class));
		System.out.println(Thread.currentThread().getName() + " Free spaces: Motorcycle [" + mFreeSpace + "/"
				+ totalMotorcycleSpaces + "], Car [" + cFreeSpace + "/" + totalCarSpaces + "]");
		if (mFreeSpace < 0 || cFreeSpace < 0) {
			new ThrowsCustomException().Error();
			System.exit(1);
		}
	}

	@Override
	public void resetDatabase() {
		records = new HashMap<>();
	}

}
