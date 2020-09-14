package com.bridgelabz.parkinglot.pojo;

import java.util.Calendar;
import java.util.Date;

public class Vehicle {

    public final String color;
    public final Date time;
    public final String vehicleType;

    public Vehicle(String color) {
        this.color = color;
        this.vehicleType = vehicleType;
        this.time = Calendar.getInstance().getTime();
    }
}
