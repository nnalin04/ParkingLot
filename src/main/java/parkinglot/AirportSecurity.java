package parkinglot;

public class AirportSecurity {


    private static boolean isFull;

    public void parkingLotFull(boolean isFull) {
        if(isFull)
            AirportSecurity.isFull = true;
    }

    public boolean isParkingLotFull() {
        if (isFull)
            return true;
        return false;
    }
}
