package parkinglot;

public class ParkingLotSystem {

    private Object vehicle;

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException("Parking Lot is Full");
        this.vehicle = vehicle;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }

    public void unPark(Object vehicle) throws ParkingLotException {
        if (vehicle == null || !this.vehicle.equals(vehicle))
            throw new ParkingLotException("No Such Vehicle In Parking Lot");
        if (this.vehicle.equals(vehicle)){
            this.vehicle = null;
        }
    }

    public  boolean isVehicleUnParked() {
        if (this.vehicle == null)
            return true;
        return false;
    }
}
