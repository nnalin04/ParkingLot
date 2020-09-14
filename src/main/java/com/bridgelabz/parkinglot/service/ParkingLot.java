package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.pojo.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    public List<Vehicle> slots;
    public int parkingCapacity;


    public ParkingLot(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        this.slots = new ArrayList<>();
    }


}
