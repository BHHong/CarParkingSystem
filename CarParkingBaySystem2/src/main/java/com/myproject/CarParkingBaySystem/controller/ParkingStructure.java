package com.myproject.CarParkingBaySystem.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.ParkingToken;

public class ParkingStructure implements ParkingStructureOffice {

	private static Map<Integer, ParkingToken> map = new ConcurrentHashMap<>();

	private int counter;
	private final int totalFreeSpaces;

	public ParkingStructure(int totalFreeSpaces) {
		this.counter = 0;
		this.totalFreeSpaces = totalFreeSpaces;
	}

	public boolean hasSpot() {
		return (map.size() < totalFreeSpaces);
	}

	@Override
	public void entryGate() {
		if (hasSpot()) {
			if (map.putIfAbsent(counter + 1, new ParkingToken(counter + 1)) == null) {
				System.out.println(Thread.currentThread().getName() + " ENTRY GATE : Token #" + ++counter + " issued. ["
						+ map.size() + "/" + totalFreeSpaces + "]");
				if(map.size() > totalFreeSpaces){
					new ThrowsCustomException().Error();
					System.exit(1);
				}
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
			System.out.println(Thread.currentThread().getName() + " EXIT GATE : Token #" + counter + " collected. ["
					+ map.size() + "/" + totalFreeSpaces + "]");
		} else {
			new ThrowsCustomException().Unpaid(counter);
		}
	}

	@Override
	public void pay(Integer counter, double payment) {
		PaymentSystem paymentSystem = new PaymentSystem(map.get(counter), payment);
		if (paymentSystem.isSuccessful()) {
			map.put(counter, paymentSystem.getParkingSpace());
			System.out.println(Thread.currentThread().getName() + " Token #" + counter
					+ " payment successful. Please proceed to exit.");
		} else {
			new ThrowsCustomException().InsufficientFund(counter);
		}
	}

	@Override
	public int capacityInfo() {
		System.out.println((totalFreeSpaces-map.size()) + " spaces available, out of " + totalFreeSpaces);
		return totalFreeSpaces-map.size();
	}

}
