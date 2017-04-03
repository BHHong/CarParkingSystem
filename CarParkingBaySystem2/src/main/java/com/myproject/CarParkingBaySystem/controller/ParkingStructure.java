package com.myproject.CarParkingBaySystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myproject.CarParkingBaySystem.exception.ThrowsCustomException;
import com.myproject.CarParkingBaySystem.model.ParkingToken;

public class ParkingStructure implements ParkingStructureOffice {

	private static Map<Integer, ParkingToken> occupancy = new ConcurrentHashMap<>();
	private int tokenCounter;
	private final int totalFreeSpaces;

	public ParkingStructure(int totalFreeSpaces) {
		this.tokenCounter = 0;
		this.totalFreeSpaces = totalFreeSpaces;
	}

	@Override
	public boolean hasSpot() {
		return (occupancy.size() < totalFreeSpaces);
	}

	@Override
	public void entryGate() {
		if (hasSpot()) {
			if (occupancy.putIfAbsent(tokenCounter + 1, new ParkingToken(tokenCounter + 1)) == null) {
				System.out.println(Thread.currentThread().getName() + " ENTRY GATE : Token #" + ++tokenCounter + " issued. ["
						+ occupancy.size() + "/" + totalFreeSpaces + "]");
				if(occupancy.size() > totalFreeSpaces){
					new ThrowsCustomException().Error();
					System.exit(1); // for testing purpose
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
	public void exitGate(Integer tokenNumber) {
		if (occupancy.get(tokenNumber).isPaid() == true) {
			occupancy.remove(tokenNumber);
			System.out.println(Thread.currentThread().getName() + " EXIT GATE : Token #" + tokenNumber + " collected. ["
					+ occupancy.size() + "/" + totalFreeSpaces + "]");
		} else {
			new ThrowsCustomException().Unpaid(tokenNumber);
		}
	}

	@Override
	public void pay(Integer tokenNumber, double payment) {
		PaymentSystem paymentSystem = new PaymentSystem(occupancy.get(tokenNumber), payment);
		if (paymentSystem.isSuccessful()) {
			occupancy.put(tokenNumber, paymentSystem.getParkingSpace());
			System.out.println(Thread.currentThread().getName() + " Token #" + tokenNumber
					+ " payment successful. Please proceed to exit.");
		} else {
			new ThrowsCustomException().InsufficientFund(tokenNumber);
		}
	}

	@Override
	public int capacityInfo() {
		System.out.println((totalFreeSpaces-occupancy.size()) + " spaces available, out of " + totalFreeSpaces);
		return totalFreeSpaces-occupancy.size();
	}

	@Override
	public List<Integer> getOccupiedInfo(){
		return new ArrayList<Integer>(occupancy.keySet());
	}
}
