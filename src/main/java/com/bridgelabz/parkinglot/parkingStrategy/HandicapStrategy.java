package com.bridgelabz.parkinglot.parkingStrategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public enum HandicapStrategy implements ParkingLotStrategy {
    HANDICAP;

    @Override
    public ParkingLot getSlot(List<ParkingLot> lots) {
        for (ParkingLot lot : lots){
            if (lot.slots.size() < lot.parkingCapacity){
                return lot;
            }
        }
        return null;
    }
}
