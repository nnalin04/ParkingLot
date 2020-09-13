package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.AirportSecurity;
import com.bridgelabz.parkinglot.model.ParkingLotOwner;
import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import com.bridgelabz.parkinglot.service.ParkingSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Vehicle vehicle1 = null;
    Vehicle vehicle2 = null;
    Vehicle vehicle3 = null;
    ParkingLotOwner owner = null;
    AirportSecurity airportSecurity = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(1);
        vehicle = new Vehicle(Rider.NORMAL);
        vehicle1 = new Vehicle(Rider.NORMAL);
        vehicle2 = new Vehicle(Rider.NORMAL);
        vehicle3 = new Vehicle(Rider.NORMAL);
        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingLotObserver(owner);
        parkingLotSystem.registerParkingLotObserver(airportSecurity);
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.isVehicleParked(vehicle1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is Full", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(vehicle1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenNoVehicle_WhenUnpParked_ShouldReturnFalse() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.totalParkingSpace(2);
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
        ParkingSlot slot1 = new ParkingSlot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.totalParkingSpace(2);
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is already parked", e.getMessage());
        }
    }

    @Test
    public void givenEmptyParkingLot_WhenOwnerKnows_ShouldReturnFalse() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        boolean isFull = owner.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenFullParkingLot_WhenAirportSecurityKnows_ShouldReturnTrue() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        boolean isFull = airportSecurity.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
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
        ParkingSlot slot1 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertFalse(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifySpace_ShouldBeParkedInThatSpace() throws ParkingLotException {
        ParkingSlot slot1 = new ParkingSlot(2);
        ParkingSlot slot2 = new ParkingSlot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.totalParkingSpace(4);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.park(vehicle1, slot1);
        ParkingSlot vehiclePosition = parkingLotSystem.getSlot(vehicle1);
        Assert.assertEquals(slot1, vehiclePosition);
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifyUnavailableSpace_ShouldBeParkedInThatSpace() {
        ParkingSlot slot1 = new ParkingSlot(1);
        ParkingSlot slot2 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.totalParkingSpace(4);
        try {
            parkingLotSystem.park(vehicle, slot2);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No such lot Present", e.getMessage());
        }
    }

    @Test
    public void givenAParkedVehicle_WhenPositionSearched_ShouldReturnPosition() throws ParkingLotException {
        ParkingSlot slot1 = new ParkingSlot(1);
        ParkingSlot slot2 = new ParkingSlot(1);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.totalParkingSpace(2);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.park(vehicle1);
        ParkingSlot vehiclePosition = parkingLotSystem.getSlot(vehicle1);
        Assert.assertEquals(slot2, vehiclePosition);
    }

    @Test
    public void givenAParkedVehicle_WhenSearchedBySlot_ShouldReturnSlotPosition() throws ParkingLotException {
        ParkingSlot slot1 = new ParkingSlot(1);
        ParkingSlot slot2 = new ParkingSlot(1);
        ParkingSlot slot3 = new ParkingSlot(1);
        ParkingSlot slot4 = new ParkingSlot(1);
        parkingLotSystem.totalParkingSpace(4);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.park(vehicle2);
        parkingLotSystem.park(vehicle3);
        ParkingSlot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        ParkingSlot parkedSlot2 = parkingLotSystem.getSlot(vehicle1);
        ParkingSlot parkedSlot3 = parkingLotSystem.getSlot(vehicle2);
        ParkingSlot parkedSlot4 = parkingLotSystem.getSlot(vehicle3);
        Assert.assertTrue(parkedSlot1 != parkedSlot2 && parkedSlot3 != parkedSlot4 );
    }

    @Test
    public void givenHandicapRider_WhenParked_ShouldReturnNearestSpaceAvailable() throws ParkingLotException {
        ParkingSlot slot1 = new ParkingSlot(2);
        ParkingSlot slot2 = new ParkingSlot(2);
        ParkingSlot slot3 = new ParkingSlot(2);
        Vehicle vehicle = new Vehicle(Rider.HANDICAP);
        parkingLotSystem.totalParkingSpace(6);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.park(vehicle);
        ParkingSlot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        Assert.assertEquals(slot1, parkedSlot1);
    }

    @Test
    public void givenLargeVehicle_WhenParkedShouldReturnTheMostSpaciousSlot() throws ParkingLotException {
        ParkingSlot slot1 = new ParkingSlot(2);
        ParkingSlot slot2 = new ParkingSlot(2);
        ParkingSlot slot3 = new ParkingSlot(2);
        Vehicle vehicle = new Vehicle(Rider.LARGE);
        parkingLotSystem.totalParkingSpace(6);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.park(vehicle1, slot3);
        parkingLotSystem.park(vehicle2, slot2);
        parkingLotSystem.park(vehicle3, slot3);
        parkingLotSystem.park(vehicle);
        ParkingSlot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        Assert.assertEquals(slot1, parkedSlot1);
    }
}