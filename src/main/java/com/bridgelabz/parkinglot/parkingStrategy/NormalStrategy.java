package com.bridgelabz.parkinglot.parkingStrategy;

import com.bridgelabz.parkinglot.service.ParkingLot;

import java.util.List;

public enum NormalStrategy implements ParkingLotStrategy {

    NORMAL;

    @Override
    public ParkingLot getSlot(List<ParkingLot> lots) {
        for (int i = 0; i < lots.size(); i++)
        {
            if (lots.get(i).slots.size() == 0)
                return lots.get(i);
            for (int j = i + 1; j < lots.size(); j++)
            {
                if (lots.get(i).slots.size() > lots.get(j).slots.size())
                {
                    ParkingLot temp = lots.get(i);
                    lots.set(i, lots.get(j));
                    lots.set(j, temp);
                }
            }
        }
        return lots.get(0);
    }
}