package com.bridgelabz.parkinglot.pojo;

import com.bridgelabz.parkinglot.Rider;

import java.util.Calendar;
import java.util.Date;

public class Vehicle {

    public Rider rider;
    public Date time;

    public Vehicle(Rider rider) {
        this.rider = rider;
        this.time = Calendar.getInstance().getTime();
    }
}
