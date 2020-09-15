package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.parkingStrategy.HandicapStrategy;
import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.service.ParkingLot;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;

import java.util.*;
import java.util.stream.Collectors;

public class PoliceActivities {

    List<Vehicle> vehicles;
    Map<Vehicle, ParkingLot> vehicleMap;
    long currentTime;
    long timeBefore30Min;

    PoliceActivities(){
        this.vehicles = new ArrayList<>();
        this.vehicleMap = new HashMap<>();
        this.currentTime = new Date().getTime();
        this.timeBefore30Min = currentTime - 1800000;
    }

    public List<Vehicle> searchWhiteVehicle(ParkingLotSystem parkingLotSystem) {
        for (ParkingLot lot : parkingLotSystem.getParkingLot()){
            vehicles.addAll(lot.slots.stream()
                    .filter(vehicle -> vehicle.color.equals("white"))
                    .collect(Collectors.toList()));
        }
        return this.vehicles;
    }

    public Map<Vehicle, ParkingLot> checkForBlueToyota(ParkingLotSystem parkingLotSystem) {
        for (ParkingLot lot : parkingLotSystem.getParkingLot()){
            for (Vehicle vehicle : lot.slots){
                if (vehicle.color.equals("blue") && vehicle.vehicleType.equals("Toyota"))
                    vehicleMap.put(vehicle, lot);
            }
        }
        return vehicleMap;
    }

    public List<Vehicle> SearchBMW(ParkingLotSystem parkingLotSystem) {
        for (ParkingLot lot : parkingLotSystem.getParkingLot()){
            vehicles.addAll(lot.slots.stream()
                    .filter(vehicle -> vehicle.vehicleType.equals("BMW"))
                    .collect(Collectors.toList()));
        }
        return vehicles;
    }

    public List<Vehicle> SearchVehicleForLast30Min(ParkingLotSystem parkingLotSystem) {
        for (ParkingLot lot : parkingLotSystem.getParkingLot()){
            vehicles.addAll(lot.slots.stream()
                                .filter(vehicle -> vehicle.time > timeBefore30Min)
                                .collect(Collectors.toList()));
        }
        return this.vehicles;
    }

    public List<Vehicle> SearchHandicapRiders(ParkingLotSystem parkingLotSystem, ParkingLot... lots) {
        for (ParkingLot lot : lots){
            vehicles.addAll(lot.slots.stream()
                    .filter(vehicle -> vehicle.riderType.equals(HandicapStrategy.HANDICAP))
                    .collect(Collectors.toList()));
        }
        return vehicles;
    }
}
