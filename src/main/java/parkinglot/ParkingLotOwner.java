package parkinglot;

public class ParkingLotOwner {

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
