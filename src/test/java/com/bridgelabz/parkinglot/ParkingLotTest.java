package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.AirportSecurity;
import com.bridgelabz.parkinglot.model.ParkingLotOwner;
import com.bridgelabz.parkinglot.parkingStrategy.HandicapStrategy;
import com.bridgelabz.parkinglot.parkingStrategy.LargeStrategy;
import com.bridgelabz.parkinglot.parkingStrategy.NormalStrategy;
import com.bridgelabz.parkinglot.pojo.Vehicle;
import com.bridgelabz.parkinglot.police.PoliceActivities;
import com.bridgelabz.parkinglot.service.ParkingLotSystem;
import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.Map;

public class ParkingLotTest {

    @Mock
    ParkingLotOwner owner;
    AirportSecurity airportSecurity;
    PoliceActivities police;
    ParkingLot slot1;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Vehicle vehicle1 = null;
    Vehicle vehicle2 = null;
    Vehicle vehicle3 = null;
    Vehicle vehicle4 = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(1);

        vehicle = new Vehicle("white", "Toyota", "ABC", "tom", NormalStrategy.NORMAL);
        vehicle1 = new Vehicle("black", "Toyota", "ABD", "tom", NormalStrategy.NORMAL);
        vehicle2 = new Vehicle("blue", "BMW", "ABE", "dic", NormalStrategy.NORMAL);
        vehicle3 = new Vehicle("white", "BMW", "ABF", "tom", NormalStrategy.NORMAL);
        vehicle4 = new Vehicle("blue", "Toyota", "ABG", "harry", NormalStrategy.NORMAL);

        slot1 = new ParkingLot(1);

        owner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        police = new PoliceActivities();

        parkingLotSystem.registerParkingLotObserver(owner);
        parkingLotSystem.registerParkingLotObserver(airportSecurity);

    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnTrue() {
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.isVehicleParked(vehicle1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is Full", e.getMessage());
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenUnPresentVehicle_WhenUnParked_ShouldReturnFalse() {
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.unPark(vehicle1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
        }
    }

    @Test
    public void givenNoVehicle_WhenUnpParked_ShouldReturnFalse() {
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.unPark(null);
        } catch (ParkingLotException e) {
            Assert.assertEquals("No Such Vehicle In Parking Lot", e.getMessage());
        }
    }

    @Test
    public void givenFullParkingLot_WhenOwnerKnows_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        boolean isFull = owner.isParkingLotFull();
        Assert.assertTrue(isFull);
    }

    @Test
    public void givenFullParkingLot_WhenMoreVehicleAdded_ShouldReturnFull() {
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking Lot is Full", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenCapacityIs2_ShouldBeAbleToParkTwoVehicle() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.totalParkingSpace(2);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked && isParked1);
    }

    @Test
    public void givenCapacityIs2AndWithSimilarVehicle_ShouldBeAbleToParkTeoVehicle() {
        ParkingLot slot1 = new ParkingLot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.totalParkingSpace(2);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle is already parked", e.getMessage());
        }
    }

    @Test
    public void givenEmptyParkingLot_WhenOwnerKnows_ShouldReturnFalse() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        boolean isFull = owner.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenFullParkingLot_WhenAirportSecurityKnows_ShouldReturnTrue() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            boolean isFull = airportSecurity.isParkingLotFull();
            Assert.assertTrue(isFull);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenEmptyParkingLot_WhenAirportSecurityKnows_ShouldReturnFalse() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        boolean isFull = airportSecurity.isParkingLotFull();
        Assert.assertFalse(isFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            parkingLotSystem.unPark(vehicle);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertTrue(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceNotAvailable_ShouldReturnTrue() {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        try {
            parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
            boolean isUnOccupied = owner.isParkingLotUnOccupied();
            Assert.assertFalse(isUnOccupied);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifySpace_ShouldBeParkedInThatSpace() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.totalParkingSpace(4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, slot1);
        ParkingLot vehiclePosition = parkingLotSystem.getSlot(vehicle1);
        Assert.assertEquals(slot1, vehiclePosition);
    }

    @Test
    public void givenParkingLot_WhenOwnerSpecifyUnavailableSpace_ShouldBeParkedInThatSpace() {
        ParkingLot slot1 = new ParkingLot(1);
        ParkingLot slot2 = new ParkingLot(1);
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
        ParkingLot slot1 = new ParkingLot(1);
        ParkingLot slot2 = new ParkingLot(1);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.totalParkingSpace(2);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        ParkingLot vehiclePosition = parkingLotSystem.getSlot(vehicle1);
        Assert.assertEquals(slot2, vehiclePosition);
    }

    @Test
    public void givenAParkedVehicle_WhenSearchedBySlot_ShouldReturnSlotPosition() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(1);
        ParkingLot slot2 = new ParkingLot(1);
        ParkingLot slot3 = new ParkingLot(1);
        ParkingLot slot4 = new ParkingLot(1);
        parkingLotSystem.totalParkingSpace(4);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle3, NormalStrategy.NORMAL);
        ParkingLot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        ParkingLot parkedSlot2 = parkingLotSystem.getSlot(vehicle1);
        ParkingLot parkedSlot3 = parkingLotSystem.getSlot(vehicle2);
        ParkingLot parkedSlot4 = parkingLotSystem.getSlot(vehicle3);
        Assert.assertTrue(parkedSlot1 != parkedSlot2 && parkedSlot3 != parkedSlot4 );
    }

    @Test
    public void givenHandicapRider_WhenParked_ShouldReturnNearestSpaceAvailable() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        Vehicle vehicle = new Vehicle
                          ("white", "BMW", "ABC", "tom", HandicapStrategy.HANDICAP);
        parkingLotSystem.totalParkingSpace(6);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.park(vehicle, HandicapStrategy.HANDICAP);
        ParkingLot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        Assert.assertEquals(slot1, parkedSlot1);
    }

    @Test
    public void givenLargeVehicle_WhenParkedShouldReturnTheMostSpaciousSlot() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        Vehicle vehicle = new Vehicle
                          ("white", "BMW","ABC", "tom", NormalStrategy.NORMAL);
        parkingLotSystem.totalParkingSpace(6);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.park(vehicle1, slot3);
        parkingLotSystem.park(vehicle2, slot2);
        parkingLotSystem.park(vehicle3, slot3);
        parkingLotSystem.park(vehicle, LargeStrategy.LARGE);
        ParkingLot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        Assert.assertEquals(slot1, parkedSlot1);
    }

    @Test
    public void givenMoreLargeVehicle_WhenParkedShouldReturnTheMostSpaciousSlot() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        Vehicle vehicle = new Vehicle
                ("white", "BMW","ABC", "tom", LargeStrategy.LARGE);
        Vehicle vehicle4 = new Vehicle
                ("white", "Lamborghini","ABC", "tom", LargeStrategy.LARGE);
        Vehicle vehicle5 = new Vehicle
                ("white", "LOTUS","ABC", "tom", LargeStrategy.LARGE);
        parkingLotSystem.totalParkingSpace(6);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.park(vehicle1, slot3);
        parkingLotSystem.park(vehicle2, slot2);
        parkingLotSystem.park(vehicle3, slot3);
        parkingLotSystem.park(vehicle, LargeStrategy.LARGE);
        parkingLotSystem.park(vehicle4, LargeStrategy.LARGE);
        parkingLotSystem.park(vehicle5, LargeStrategy.LARGE);
        ParkingLot parkedSlot1 = parkingLotSystem.getSlot(vehicle);
        Assert.assertEquals(slot1, parkedSlot1);
    }



    @Test
    public void givenVehicleWithColor_WhenAskedForWhiteColor_ShouldReturnWhiteVehicleList() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(1);
        ParkingLot slot2 = new ParkingLot(1);
        ParkingLot slot3 = new ParkingLot(1);
        ParkingLot slot4 = new ParkingLot(1);
        parkingLotSystem.totalParkingSpace(4);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle3, NormalStrategy.NORMAL);
        List<Vehicle> whiteVehicles = police.searchVehicle(parkingLotSystem);
        boolean hasVehicle = whiteVehicles.contains(vehicle);
        boolean hasVehicle1 = whiteVehicles.contains(vehicle3);
        Assert.assertTrue(hasVehicle && hasVehicle1);
    }

    @Test
    public void givenBlackVehicle_WhenAskedForWhiteColor_ShouldReturnFalse() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(1);
        parkingLotSystem.totalParkingSpace(1);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        List<Vehicle> whiteVehicles = police.searchVehicle(parkingLotSystem);
        boolean hasVehicle = whiteVehicles.contains(vehicle1);
        Assert.assertFalse(hasVehicle);
    }

    @Test
    public void givenVehicles_WhenBlueToyotaSearched_ShouldReturnDetail() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        ParkingLot slot4 = new ParkingLot(2);
        parkingLotSystem.totalParkingSpace(8);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle4, NormalStrategy.NORMAL);
        Map<Vehicle, ParkingLot> vehicles = police.checkForBlueToyota(parkingLotSystem);
        boolean hasVehicle = vehicles.containsKey(vehicle4);
        boolean hasVehicle1 = !vehicles.containsKey(vehicle2);
        Assert.assertTrue(hasVehicle && hasVehicle1);
    }

    @Test
    public void givenVehicles_WhenBMWSearched_ShouldReturnVehicleList() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        ParkingLot slot4 = new ParkingLot(2);
        parkingLotSystem.totalParkingSpace(8);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle4, NormalStrategy.NORMAL);
        List<Vehicle> vehicles = police.SearchBMW(parkingLotSystem);
        boolean hasVehicle = vehicles.contains(vehicle2);
        boolean hasVehicle1 = !vehicles.contains(vehicle4);
        Assert.assertTrue(hasVehicle && hasVehicle1);
    }

    @Test
    public void givenVehicles_WhenSearchedForLast30Min_ShouldReturnVehicleList() throws ParkingLotException {
        ParkingLot slot1 = new ParkingLot(2);
        ParkingLot slot2 = new ParkingLot(2);
        ParkingLot slot3 = new ParkingLot(2);
        ParkingLot slot4 = new ParkingLot(2);
        parkingLotSystem.totalParkingSpace(8);
        parkingLotSystem.setParkingLot(slot1);
        parkingLotSystem.setParkingLot(slot2);
        parkingLotSystem.setParkingLot(slot3);
        parkingLotSystem.setParkingLot(slot4);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle4, NormalStrategy.NORMAL);
        List<Vehicle> vehicles = police.SearchVehicleForLast30Min(parkingLotSystem);
        boolean hasVehicle = vehicles.contains(vehicle);
        boolean hasVehicle1 = vehicles.contains(vehicle1);
        boolean hasVehicle2 = vehicles.contains(vehicle2);
        boolean hasVehicle4 = vehicles.contains(vehicle4);
        Assert.assertTrue(hasVehicle && hasVehicle1 && hasVehicle2 && hasVehicle4);
    }

    @Test
    public void givenHandicapVehicles_WhenSearchedInBAndDLot_ShouldReturnVehicleDetail()
            throws ParkingLotException {
        ParkingLot A = new ParkingLot(2);
        ParkingLot B = new ParkingLot(2);
        ParkingLot C = new ParkingLot(2);
        ParkingLot D = new ParkingLot(2);
        parkingLotSystem.totalParkingSpace(8);
        vehicle = new Vehicle
                  ("white", "BMW", "ABC", "tom", HandicapStrategy.HANDICAP);
        parkingLotSystem.setParkingLot(A);
        parkingLotSystem.setParkingLot(B);
        parkingLotSystem.setParkingLot(C);
        parkingLotSystem.setParkingLot(D);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle4, NormalStrategy.NORMAL);
        List<Vehicle> vehicles = police.SearchHandicapRiders(A, B);
        boolean hasVehicle = vehicles.contains(vehicle);
        boolean hasVehicle1 = !vehicles.contains(vehicle1);
        boolean hasVehicle2 = !vehicles.contains(vehicle2);
        boolean hasVehicle4 = !vehicles.contains(vehicle4);
        Assert.assertTrue(hasVehicle && hasVehicle1 && hasVehicle2 && hasVehicle4);
    }

    @Test
    public void givenVehicles_WhenCheckedForFraudulentPlate_ShouldReturnVehicleDetail() throws ParkingLotException {
        ParkingLot A = new ParkingLot(2);
        ParkingLot B = new ParkingLot(2);
        ParkingLot C = new ParkingLot(2);
        ParkingLot D = new ParkingLot(2);
        parkingLotSystem.totalParkingSpace(8);
        vehicle = new Vehicle("white", "BMW", "ABC", "tom", HandicapStrategy.HANDICAP);
        parkingLotSystem.setParkingLot(A);
        parkingLotSystem.setParkingLot(B);
        parkingLotSystem.setParkingLot(C);
        parkingLotSystem.setParkingLot(D);
        parkingLotSystem.park(vehicle, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle1, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle2, NormalStrategy.NORMAL);
        parkingLotSystem.park(vehicle4, NormalStrategy.NORMAL);
        List<Vehicle> vehicles = police.SearchForFraudulentPlate(parkingLotSystem);
        Assert.assertEquals(0, vehicles.size());
    }
}
