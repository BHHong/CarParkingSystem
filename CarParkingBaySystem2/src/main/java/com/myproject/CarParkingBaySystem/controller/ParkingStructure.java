package com.myproject.CarParkingBaySystem.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.ParkingToken;

public class ParkingStructure implements ParkingStructureOffice {

	private static Map<Integer, ParkingToken> map = new ConcurrentHashMap<>();

	private int counter;
	private int freeSpaces;
	private final int totalFreeSpaces; 
	
	public ParkingStructure(int totalFreeSpaces) {
		this.counter = 0;
		this.freeSpaces = totalFreeSpaces;
		this.totalFreeSpaces = totalFreeSpaces;
	}

	public boolean hasSpot() {
		return (freeSpaces > 0);
	}

	@Override
	public void entryGate() {
		if (hasSpot()) {
			if (map.putIfAbsent(counter + 1, new ParkingToken(counter + 1)) == null) {
				freeSpaces--;
				counter++;
				System.out.println(Thread.currentThread().getName() + " ENTRY GATE : Token #" + counter + " issued.");
			} else {
				System.out.println(Thread.currentThread().getName() + " Retry findSpot.");
				entryGate();
			}
		} else {
			new ThrowsCustomException().NoSpot();
		}
	}

	@Override
	public void exitGate(Integer counter) {
		if (map.get(counter).isPaid() == true) {
			map.remove(counter);
			freeSpaces++;
			System.out.println(Thread.currentThread().getName() + " EXIT GATE : Token #" + counter + " collected.");
		} else {
			new ThrowsCustomException().Unpaid(counter);
		}
	}

	@Override
	public void pay(Integer counter, double payment) {
		PaymentSystem paymentSystem = new PaymentSystem(map.get(counter), payment);
		if (paymentSystem.isSuccessful()) {
			map.put(counter, paymentSystem.getParkingSpace());
			System.out.println(Thread.currentThread().getName() + " Token #" + counter + " payment successful. Please proceed to exit.");
		} else {
			new ThrowsCustomException().InsufficientFund(counter);
		}
	}

	@Override
	public void capacityInfo() {
		System.out.println(freeSpaces + " spaces available, out of " + totalFreeSpaces);
		
	}

}
