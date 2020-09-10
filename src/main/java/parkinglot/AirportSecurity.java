package parkinglot;

public class AirportSecurity  implements ParkingLotObserver{

    private boolean isFull;

    public void parkingLotFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isParkingLotFull() {
        return isFull;
    }
}
