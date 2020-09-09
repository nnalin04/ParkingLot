package parkinglot;

public class ParkingLotSystem {

    private Object vehicle;
    Owner owner = new Owner();

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException("Parking Lot is Full");
        this.vehicle = vehicle;
        owner.parkingLotFull(this.vehicle != null);
    }

    public boolean isVehicleParked(Object vehicle) {
        return this.vehicle.equals(vehicle);
    }

    public void unPark(Object vehicle) throws ParkingLotException {
        if (vehicle == null || !this.vehicle.equals(vehicle))
            throw new ParkingLotException("No Such Vehicle In Parking Lot");
        if (this.vehicle.equals(vehicle)){
            this.vehicle = null;
        }
    }

    public  boolean isVehicleUnParked() {
        return this.vehicle == null;
    }
}
