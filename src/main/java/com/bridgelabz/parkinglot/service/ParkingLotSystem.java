package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.parkingStrategy.ParkingLotStrategy;
import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;

import java.util.*;

public class ParkingLotSystem {

    private int capacity;
    private List<ParkingLot> lots;
    private List<Vehicle> vehicles;
    private List<ParkingLotObserver> observers;

    public ParkingLotSystem(int capacity) {
        this.lots = new ArrayList();
        this.capacity = capacity;
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList();
    }

    public void setParkingLot(ParkingLot lot) {
        this.lots.add(lot);
    }

    public void totalParkingSpace(int capacity) {
        this.capacity = capacity;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(Vehicle vehicle, ParkingLotStrategy strategy) throws ParkingLotException {
        ParkingLot lot;
        ParkingLotStrategy parkingLotStrategy;
        if (this.vehicles.size() == this.capacity)
            throw new ParkingLotException("Parking Lot is Full");
        if (this.lots.contains(getSlot(vehicle)))
            throw new ParkingLotException("Vehicle is already parked");
        parkingLotStrategy = strategy;
        lot = parkingLotStrategy.getSlot(lots);
        lot.slots.add(vehicle);
        this.vehicles.add(vehicle);
        for (ParkingLotObserver observer : observers) {
            observer.parkingLotFull(this.vehicles.size() == this.capacity);
        }
    }

    public void park(Vehicle vehicle, ParkingLot... lots) throws ParkingLotException {
        if (lots.length > 0) {
            if (!this.lots.contains(lots[0]))
                throw new ParkingLotException("No such lot Present");
            lots[0].slots.add(vehicle);
        }
    }


    public boolean isVehicleParked(Vehicle vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null || !this.vehicles.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle In Parking Lot");
        if (this.vehicles.contains(vehicle)){
            ParkingLot lot = this.getSlot(vehicle);
            lot.slots.remove(vehicle);
            this.vehicles.remove(vehicle);
            for (ParkingLotObserver observer : observers) {
                observer.parkingLotFull(this.vehicles.size() == this.capacity);
            }
        }
    }

    public  boolean isVehicleUnParked(Vehicle vehicle) {
        return !this.vehicles.contains(vehicle);
    }

    public ParkingLot getSlot(Vehicle vehicle) {
        for (ParkingLot lot : this.lots){
            if (lot.slots.contains(vehicle)){
                return lot;
            }
        }
        return null;
    }

    public List<ParkingLot> gatParkingLot() {
        return this.lots;
    }
}