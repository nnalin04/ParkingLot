package com.bridgelabz.parkinglot.parkingStrategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public enum NormalStrategy implements ParkingLotStrategy {
    NORMAL;
    int minSize;
    int size;

    @Override
    public ParkingLot getSlot(List<ParkingLot> lots) {

        if (lots.size() == 1){
            return lots.get(0);
        }

        ParkingLot slot = null;
        for (ParkingLot lot : lots){
            size = lot.slots.size();
            if (size == 0){
                slot =  lot;
                break;
            } else if (size <= minSize){
                this.minSize = size;
                slot = lot;
            }
        }
        return slot;
    }
}
