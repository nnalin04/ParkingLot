package com.bridgelabz.parkinglot.parkingStrategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public interface ParkingLotStrategy {
    ParkingLot getSlot(List<ParkingLot> lots);
}
