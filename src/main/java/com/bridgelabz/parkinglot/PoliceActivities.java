package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;

import java.util.ArrayList;
import java.util.List;

public class PoliceActivities {

    List<Vehicle> whiteVehicle;

    PoliceActivities(){
        this.whiteVehicle = new ArrayList<>();
    }

    public List<Vehicle> checkWhiteCars(ParkingLotSystem parkingLotSystem) {
        for (ParkingLot lot : parkingLotSystem.gatParkingLot()){
            for (Vehicle vehicle : lot.slots){
                if (vehicle.color == "white")
                    whiteVehicle.add(vehicle);
            }
        }
        return this.whiteVehicle;
    }
}
