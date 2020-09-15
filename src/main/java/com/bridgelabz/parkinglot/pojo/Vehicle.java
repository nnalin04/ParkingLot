package com.bridgelabz.parkinglot.pojo;

import com.bridgelabz.parkinglot.parkingStrategy.ParkingLotStrategy;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Vehicle {

    public final String color;
    public final long time;
    public final String vehicleType;
    private final String attendant;
    private final String plateNo;
    public ParkingLotStrategy riderType;

    public Vehicle(String color, String vehicleType, String plateNo, String attendant, ParkingLotStrategy strategy) {
        this.plateNo = plateNo;
        this.attendant = attendant;
        this.color = color;
        this.vehicleType = vehicleType;
        this.time = new Date().getTime();
        this.riderType = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(color, vehicle.color) &&
                Objects.equals(time, vehicle.time) &&
                Objects.equals(vehicleType, vehicle.vehicleType) &&
                Objects.equals(attendant, vehicle.attendant) &&
                Objects.equals(plateNo, vehicle.plateNo);
    }
}
