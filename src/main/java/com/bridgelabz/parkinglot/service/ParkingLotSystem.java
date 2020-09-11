package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {

    private int capacity;
    private List vehicles;
    private List<ParkingLotObserver> observers;

    public ParkingLotSystem(int capacity) {
        this.observers = new ArrayList<>();
        vehicles = new ArrayList();
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        int k = capacity;
        while(k != 0){
            vehicles.add(null);
            k--;
        }
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(Object vehicle, int... position) throws ParkingLotException {
        if (this.vehicles.size() == this.capacity && !this.vehicles.contains(null))
            throw new ParkingLotException("Parking Lot is Full");
        if (this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle is already parked");
        if (position != null){
            if (position[0] > capacity || position[0] < 0)
                throw new ParkingLotException("No such Space Present");
            this.vehicles.set(position[0] - 1, vehicle);
        } else if (capacity > 0) this.vehicles.set(vehicles.indexOf(null), vehicle);
        else this.vehicles.add(vehicle);
        for (ParkingLotObserver observer : observers) {
            observer.parkingLotFull(this.vehicles.size() == this.capacity && !this.vehicles.contains(null));
        }
    }

    public int getVehiclePosition(Object vehicle) {
        return this.vehicles.indexOf(vehicle) + 1;
    }

    public boolean isVehicleParked(Object vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public void unPark(Object vehicle) throws ParkingLotException {
        if (vehicle == null || !this.vehicles.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle In Parking Lot");
        if (this.vehicles.contains(vehicle)){
            this.vehicles.remove(vehicle);
            for (ParkingLotObserver observer : observers) {
                observer.parkingLotFull(this.vehicles.size() > this.capacity);
            }
        }
    }

    public  boolean isVehicleUnParked(Object vehicle) {
        return !this.vehicles.contains(vehicle);
    }
}
