package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = null;
    Object vehicle = null;
    ParkingLotOwner owner = null;
    AirportSecurity airportSecurity = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Object();
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(owner);
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.isVehicleParked(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is Full", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(vehicle);
            boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
            Assert.assertTrue(isUnParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUnPresentVehicle_WhenUnParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenNoVehicle_WhenUnpParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenFullParkingLot_WhenOwnerKnows_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isFull = owner.isParkingLotFull();
            Assert.assertTrue(isFull);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCapacityIs2_ShouldBeAbleToParkTeoVehicle() {
        parkingLotSystem.setCapacity(2);
        Object vehicle1 = new Object();
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle1);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle1);
            Assert.assertTrue(isParked && isParked1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCapacityIs2AndWithSimilarVehicle_ShouldBeAbleToParkTeoVehicle() {
        parkingLotSystem.setCapacity(2);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is already parked", e.getMessage());
        }
    }

    @Test
    public void givenEmptyParkingLot_WhenOwnerKnows_ShouldReturnFalse() {
        boolean isFull = owner.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenFullParkingLot_WhenAirportSecurityKnows_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isFull = airportSecurity.isParkingLotFull();
            Assert.assertTrue(isFull);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenEmptyParkingLot_WhenAirportSecurityKnows_ShouldReturnFalse() {
        boolean isFull = airportSecurity.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(vehicle);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertTrue(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceNotAvailable_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertFalse(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
