package parkinglot;

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
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.capacity)
            throw new ParkingLotException("Parking Lot is Full");
        if (this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle is already parked");
        this.vehicles.add(vehicle);
        for (ParkingLotObserver observer : observers) {
            observer.parkingLotFull(this.vehicles.size() == this.capacity);
        }
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
