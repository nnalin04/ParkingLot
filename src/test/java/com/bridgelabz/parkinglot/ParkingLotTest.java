package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.AirportSecurity;
import com.bridgelabz.parkinglot.model.ParkingLotOwner;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
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
            parkingLotSystem.park(vehicle, null);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.isVehicleParked(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is Full", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle, null);
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
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.unPark(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenNoVehicle_WhenUnpParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.unPark(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenFullParkingLot_WhenOwnerKnows_ShouldReturnTrue() {
        try {
            parkingLotSystem.park(vehicle, null);
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
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.park(vehicle1, null);
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
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.park(vehicle, null);
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
            parkingLotSystem.park(vehicle, null);
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
            parkingLotSystem.park(vehicle, null);
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
            parkingLotSystem.park(vehicle, null);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertFalse(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifySpace_ShouldBeParkedInThatSpace() throws ParkingLotException {
        parkingLotSystem.setCapacity(4);
        parkingLotSystem.park(vehicle, 2);
        int vehiclePosition = parkingLotSystem.getVehiclePosition(vehicle);
        Assert.assertEquals(2, vehiclePosition);
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifyUnavailableSpace_ShouldBeParkedInThatSpace() {
        parkingLotSystem.setCapacity(4);
        try {
            parkingLotSystem.park(vehicle, 5);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No such Space Present", e.getMessage());
        }
    }

    @Test
    public void givenAParkedVehicle_WhenPositionSearched_ShouldReturnPosition() throws ParkingLotException {
        parkingLotSystem.setCapacity(2);
        Object vehicle1 = new Object();
            parkingLotSystem.park(vehicle, null);
            parkingLotSystem.park(vehicle1, null);
            int vehiclePosition = parkingLotSystem.getVehiclePosition(vehicle1);
            Assert.assertEquals(2, vehiclePosition);
    }
}