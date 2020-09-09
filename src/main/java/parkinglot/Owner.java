package parkinglot;

public class Owner {

    private static boolean isFull = false;

    public void parkingLotFull(boolean isFull) {
        if(isFull)
            Owner.isFull = true;
    }

    public boolean parkingLotIsFull() {
        if (isFull)
            return true;
        return false;
    }
}
