package com.bridgelabz.parkinglot.service;

import com.bridgelabz.parkinglot.Rider;
import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;

import java.util.*;

public class ParkingLotSystem {

    private int capacity;
    private List<Vehicle> vehicles;
    private List<ParkingLotObserver> observers;
    private Map<ParkingSlot, List<Vehicle>> parkingLotSlot;
    private ParkingSlot slot;

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList();
        this.parkingLotSlot = new HashMap<>();
    }

    public void setParkingLot(ParkingSlot slot) {
        List<Vehicle> vehicles = new ArrayList<>();
        this.parkingLotSlot.put(slot, vehicles);
    }

    public void totalParkingSpace(int capacity) {
        this.capacity = capacity;
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    private void availableSlots(Rider rideType) {
        int minSize = 0;
        int size = 0;
        if (rideType == Rider.HANDICAP){
            for (ParkingSlot slots : this.parkingLotSlot.keySet()) {
                if (parkingLotSlot.get(slots).size() < slots.parkingCapacity)
                    this.slot = slots;
            }
        }
        if (rideType == Rider.NORMAL || rideType == Rider.LARGE){
            for (ParkingSlot slots : this.parkingLotSlot.keySet()) {
                size = parkingLotSlot.get(slots).size();
                if (size == 0){
                    this.slot = slots;
                    break;
                } else if (size < minSize){
                    minSize = size;
                    this.slot = slots;
                }
            }
        }
    }

    public void park(Vehicle vehicle, ParkingSlot... slots) throws ParkingLotException {
        if (this.vehicles.size() == this.capacity)
            throw new ParkingLotException("Parking Lot is Full");
        if (this.parkingLotSlot.containsValue(vehicle))
            throw new ParkingLotException("Vehicle is already parked");
        if (slots.length > 0) {
            if (!parkingLotSlot.containsKey(slots[0]))
                throw new ParkingLotException("No such lot Present");
            this.slot = slots[0];
        } else {
            if (vehicle.rider == Rider.HANDICAP)
                this.availableSlots(Rider.HANDICAP);
            if (vehicle.rider == Rider.NORMAL)
                this.availableSlots(Rider.NORMAL);
            if (vehicle.rider == Rider.LARGE)
                this.availableSlots(Rider.LARGE);
        }
        parkingLotSlot.get(this.slot).add(vehicle);
        this.vehicles.add(vehicle);

        for (ParkingLotObserver observer : observers) {
            observer.parkingLotFull(this.vehicles.size() == this.capacity);
        }
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null || !this.vehicles.contains(vehicle))
            throw new ParkingLotException("No Such Vehicle In Parking Lot");
        if (this.vehicles.contains(vehicle)){
            ParkingSlot slot = this.getSlot(vehicle);
            this.parkingLotSlot.get(slot).remove(vehicle);
            this.vehicles.remove(vehicle);
            for (ParkingLotObserver observer : observers) {
                observer.parkingLotFull(this.vehicles.size() == this.capacity);
            }
        }
    }

    public  boolean isVehicleUnParked(Vehicle vehicle) {
        return !this.vehicles.contains(vehicle);
    }

    public ParkingSlot getSlot(Vehicle vehicle) {
        ParkingSlot slots = null;
        for (ParkingSlot slot : parkingLotSlot.keySet()){
            if (parkingLotSlot.get(slot).contains(vehicle)){
                slots = slot;
                break;
            }
        }
        return slots;
    }
}