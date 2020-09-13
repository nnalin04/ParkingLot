package com.bridgelabz.parkinglot;

import java.util.Calendar;
import java.util.Date;

public class Vehicle {

    public Date time;

    public Vehicle() {
        this.time = Calendar.getInstance().getTime();
    }
}
