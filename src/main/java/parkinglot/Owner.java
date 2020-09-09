package parkinglot;

public class Owner {

    private static boolean isFull = false;

    public void parkingLotFull(boolean isFull) {
        Owner.isFull = isFull;
    }

    public boolean isParkingLotFull() {
        if (isFull)
            return true;
        return false;
    }

    public boolean isParkingLotUnOccupied() {
        if (!isFull)
            return true;
        return false;
    }
}
