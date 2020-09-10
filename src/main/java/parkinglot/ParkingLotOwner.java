package parkinglot;

public class ParkingLotOwner implements ParkingLotObserver{

    private boolean isFull;

    public void parkingLotFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isParkingLotFull() {
        return this.isFull;
    }

    public boolean isParkingLotUnOccupied() {
        return !this.isFull;
    }
}
