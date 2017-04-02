package com.myproject.CarParkingBaySystem.view;

import com.myproject.CarParkingBaySystem.controller.ParkingStructure;
import com.myproject.CarParkingBaySystem.controller.ParkingStructureOffice;

public class Main {

	public static void main(String[] args) {
		ParkingStructureOffice pso = new ParkingStructure(3);
		
		pso.entryGate();
		pso.entryGate();
		pso.entryGate();
		pso.entryGate();
		pso.entryGate();
		
		pso.pay(1, 10);
		pso.pay(3, 50);
		pso.exitGate(1);
		pso.exitGate(2);
		pso.exitGate(3);
		
		pso.entryGate();
		pso.entryGate();

	}

}
