package com.bridgelabz.parkinglot;

import com.bridgelabz.Exception.ParkingLotSystemException;
import com.bridgelabz.enums.DriverType;
import com.bridgelabz.enums.VehicleSize;
import com.bridgelabz.model.AirportSecurity;
import com.bridgelabz.model.ParkingOwner;
import com.bridgelabz.model.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystemTest {
    private Vehicle vehicle;
    private ParkingLotSystem parkingLotSystem;
    private ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicle = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLot = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot);
    }

    //UC1
    @Test
    public void givenVehicleToPark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleToPark_WhenNotParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            boolean isParked = parkingLotSystem.isVehicleParked(new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal"));
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicleToPark_WhenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenVehicleToPark_WhenSameVehicleAlreadyParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Already Parked", e.getMessage());
        }
    }

    //UC2
    @Test
    public void givenVehicleToUnPark_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        boolean unparkVehicle = parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertTrue(unparkVehicle);
    }

    @Test
    public void givenVehicleToUnPark_WhenNotParked_ShouldReturnException() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        try {
            parkingLotSystem.unparkVehicle(new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal"));
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //UC3
    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsNotObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.unsubscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertFalse(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    //UC4
    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndAirportSecurityIsNotObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.unsubscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertFalse(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFullAndParkingOwnerAndAirportSecurityAreObserver_ShouldInformBothParkingOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    //UC5
    @Test
    public void givenVehicle_WhenParkingAvailableAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
    }

    @Test
    public void givenVehicle_WhenParkingAvailableAndAirportSecurityIsObserver_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(airportSecurity.isParkingFull());
    }

    @Test
    public void givenVehicle_WhenParkingAvailableAndParkingOwnerAndAirportSecurityAreObserver_ShouldInformBothParkingOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.subscribe(airportSecurity);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            Assert.assertTrue(parkingOwner.isParkingFull());
            Assert.assertTrue(airportSecurity.isParkingFull());
            parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
        parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertFalse(parkingOwner.isParkingFull());
        Assert.assertFalse(airportSecurity.isParkingFull());
    }

    //UC6
    @Test
    public void givenParkingLotSystem_WhenParkingCapacityIsSet_ShouldReturnParkingCapacity() {
        int parkingLotCapacity = parkingLot.setCapacity(100);
        Assert.assertEquals(100, parkingLotCapacity);
    }

    @Test
    public void givenParkingLotSystem_WhenListOfEmptySlotsCalled_ShouldReturnAvailableSlots() {
        List<Integer> expectedList = new ArrayList();
        expectedList.add(0);
        parkingLot.setCapacity(2);
        List<Integer> listOfEmptyParkingSlots = parkingLot.getListOfEmptyParkingSlots();
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        listOfEmptyParkingSlots = parkingLot.getListOfEmptyParkingSlots();
        Assert.assertEquals(expectedList, listOfEmptyParkingSlots);
    }

    //UC7
    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        int slotNumber = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(9, slotNumber);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleNotFound_ShouldReturnException() {
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.findVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //UC8
    @Test
    public void givenParkingLot_WhenVehicleParkedAndOwnerIsObserver_ShouldInformParkingTimeToOwner() {
        ParkingOwner parkingOwner = new ParkingOwner();
        parkingLotSystem.subscribe(parkingOwner);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = parkingOwner.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerIsObserver_ShouldReturnException() {
        ParkingOwner parkingOwner = new ParkingOwner();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.subscribe(parkingOwner);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.getVehicleParkingTime(vehicle1);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndAirportSecurityIsObserver_ShouldInformParkingTimeToAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime = airportSecurity.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndAirportSecurityIsObserver_ShouldReturnException() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.getVehicleParkingTime(vehicle1);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndOwnerAndAirportSecurityIsObserver_ShouldInformParkingTimeToBothOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = new ParkingOwner();
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        int parkingTime1 = parkingOwner.getParkingTime();
        int parkingTime2 = airportSecurity.getParkingTime();
        Assert.assertEquals(0, parkingTime1);
        Assert.assertEquals(0, parkingTime2);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerAndAirportSecurityIsObserver_ShouldReturnException() {
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.setCapacity(10);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            parkingLotSystem.getVehicleParkingTime(vehicle1);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //UC9
    @Test
    public void givenParkingLotSystem_WhenAddedLots_ShouldReturnTrue() {
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenNotAddedLots_ShouldReturnFalse() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    @Test
    public void givenMultipleVehiclesLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        parkingLot.setCapacity(5);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.unparkVehicle(vehicle);
        parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, VehicleSize.SMALL);
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(0, lastEmptySlot);
    }

    @Test
    public void givenMultipleVehiclesAtMultipleParkingLotsLessThanActualCapacity_WhenParkEvenly_shouldReturnFirstIndexEmpty() {
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle4 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);          //Lot1Spot1
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);     //Lot2Spot1
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);     //Lot1Spot2
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);     //Lot2Spot2
        parkingLotSystem.unparkVehicle(vehicle);        //Remove Lot1Spot1
        parkingLotSystem.parkVehicle(vehicle4, DriverType.NORMAL, VehicleSize.SMALL);     ////Lot1Spot1
        Object lastEmptySlot = parkingLot.getListOfEmptyParkingSlots().get(0);
        Object lastEmptySlot2 = parkingLot2.getListOfEmptyParkingSlots().get(0);
        Assert.assertEquals(0, lastEmptySlot);
        Assert.assertEquals(0, lastEmptySlot2);
    }

    //UC10
    @Test
    public void givenVehicleToPark_whenDriverIsHandicap_shouldParkedAtNearestSpot() {
        parkingLot.setCapacity(5);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);               //slot4
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);          //slot3
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);              //slot2
        parkingLotSystem.parkVehicle(vehicle3, DriverType.HANDICAP, VehicleSize.SMALL);        //slot0
        parkingLotSystem.unparkVehicle(vehicle2);                               //Remove slot2
        parkingLotSystem.unparkVehicle(vehicle);                                //Remove slot4
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);               //slot4
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);            //slot1
        int vehicleParkedLocation = parkingLot.findVehicle(vehicle2);
        Assert.assertEquals(1, vehicleParkedLocation);
    }

    @Test
    public void givenMultipleVehiclesAtMultipleParkingLots_WhenParkEvenly_shouldParkVehicleBasedOnDriverType() {
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);          //Lot1Spot2
        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.SMALL);             //Lot2Spot0
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);            //Lot1Spot0
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);          //Lot2Spot2
        int vehicleParkedLocation = parkingLotSystem.findVehicle(vehicle);
        int vehicleParkedLocation1 = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);
        Assert.assertEquals(0, vehicleParkedLocation1);
    }

    //UC11
    @Test
    public void givenLargeVehicleToPark_WhenParkingLotHasSpace_ShouldParkVehicle() {
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.LARGE);
        int spot = parkingLotSystem.findVehicle(vehicle);
        int spot1 = parkingLotSystem.findVehicle(vehicle1);
        Assert.assertEquals(0, spot);
        Assert.assertEquals(1, spot1);
    }

    @Test
    public void givenLargeVehicleToPark_WhenParkingLotHasSpaceAndDriverIsHandicap_ShouldParkVehicle() {
        parkingLot.setCapacity(5);
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.LARGE);
        int spot = parkingLotSystem.findVehicle(vehicle);
        int spot1 = parkingLotSystem.findVehicle(vehicle1);
        Assert.assertEquals(0, spot);
        Assert.assertEquals(4, spot1);
    }

    @Test
    public void givenMultipleVehiclesAtMultipleParkingLots_WhenParkEvenly_shouldParkVehicle() {
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLotSystem.addLot(parkingLot2);
        parkingLot.setCapacity(3);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);          //Lot1Spot2
        parkingLotSystem.parkVehicle(vehicle, DriverType.HANDICAP, VehicleSize.LARGE);             //Lot2Spot0
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);            //Lot1Spot0
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.LARGE);          //Lot2Spot2
        int vehicleParkedLocation = parkingLotSystem.findVehicle(vehicle);
        int vehicleParkedLocation1 = parkingLotSystem.findVehicle(vehicle2);
        Assert.assertEquals(0, vehicleParkedLocation);
        Assert.assertEquals(0, vehicleParkedLocation1);
    }

    //UC12
    @Test
    public void givenWhiteCarToPark_whenParkingLotToParkVehicle_shouldReturnLocationOfAllWhiteCars() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        lot1.add(0);
        lot1.add(1);
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<Integer>> vehicleByColor = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenBlueCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllWhiteCars() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        lot1.add(0);
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle2 = new Vehicle("BLUE", "TOYOTA", "MH-12-1235", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<Integer>> vehicleByColor = parkingLotSystem.findVehicleByColor("BLUE");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenNoWhiteCarToPark_WhenParkingLotToParkVehicle_ShouldReturnEmptyList() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("BLACK", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<Integer>> vehicleByColor = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenWhiteCarToPark_whenMultipleParkingLotToParkVehicle_shouldReturnLocationOfAllWhiteCars() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        List<Integer> lot2 = new ArrayList<>();
        lot1.add(0);
        lot2.add(0);
        expectedList.add(lot1);
        expectedList.add(lot2);
        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<Integer>> vehicleByColor = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    //UC13
    @Test
    public void givenParkingLotSystem_WhenParkedBlueToyotaCar_ShouldReturnLocationOfAllBlueToyotaCars() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> carList = new ArrayList();
        carList.add("1 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(carList);
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleByColorAndModel("BLUE", "TOYOTA");
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedWhiteBMWCar_ShouldReturnLocationOfAllWhiteBMWCars() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> carList = new ArrayList();
        carList.add("0 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(carList);
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "BMW", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleByColorAndModel("WHITE", "BMW");
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenNoBlueToyotaCarToPark_WhenParkingLotToParkVehicle_ShouldReturnEmptyList() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("BLACK", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByColorAndModel("BLUE", "Rituja");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenBlueToyotaCar_WhenMultipleParkingLotToParkVehicle_ShouldReturnLocationOfAllBlueTotoyotaCar() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        List<String> lot2 = new ArrayList<>();
        lot1.add("0 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        lot2.add("0 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot1);
        expectedList.add(lot2);

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleByColorAndModel("BLUE", "TOYOTA");
        Assert.assertEquals(expectedList, vehicleList);
    }

    //UC14
    @Test
    public void givenBMWCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllBMWCars() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByModel("BMW");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenToyotaCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllBMWCars() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("0 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByModel("TOYOTA");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenNoBMWCarToPark_WhenParkingLotToParkVehicle_ShouldReturnEmptyList() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("BLACK", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByModel("BMW");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenBMWCar_WhenMultipleParkingLotToParkVehicle_ShouldReturnLocationOfAllBMWCar() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        List<String> lot2 = new ArrayList<>();
        lot1.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        lot2.add("0 Vehicle{color='BLACK', model='BMW', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);
        expectedList.add(lot2);

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByModel("BMW");
        Assert.assertEquals(expectedList, vehicleByColor);
    }
    //UC15
    @Test
    public void givenCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllCarsParkedWithin30Miutes() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("0 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Ritu");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByTime = parkingLotSystem.findVehicleByTime(30);
        Assert.assertEquals(expectedList, vehicleByTime);
    }

    @Test
    public void givenCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllCarsParkedWithin10Miutes() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("0 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Ritu");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByTime = parkingLotSystem.findVehicleByTime(10);
        Assert.assertEquals(expectedList, vehicleByTime);
    }

    @Test
    public void givenCarToPark_WhenParkingLotToParkVehicle_ShouldReturnEmptyListWhenTimIslessThan0Mins() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("BLACK", "TOYOTA", "MH-12-1234", "Ritesh");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByTime = parkingLotSystem.findVehicleByModel("0");
        Assert.assertEquals(expectedList, vehicleByTime);
    }

    @Test
    public void givenCarToPark_WhenMultipleParkingLotToParkVehicle_ShouldReturnLocationOfAllCarsParkedWithin10Miutes() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        List<String> lot2 = new ArrayList<>();
        lot1.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        lot2.add("0 Vehicle{color='BLACK', model='BMW', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);
        expectedList.add(lot2);

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleByTime = parkingLotSystem.findVehicleByTime(30);
        Assert.assertEquals(expectedList, vehicleByTime);
    }
    //UC16
    @Test
    public void givenParkingLotSystem_WhenParkedSmallVehicleAndDriverIsHandicap_ShouldReturnDetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("1 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot);
        parkingLot.setCapacity(3);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.HANDICAP, 1);
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedLargeVehicleAndDriverIsHandicap_ShouldReturnDetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot);
        parkingLot.setCapacity(3);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.LARGE, DriverType.HANDICAP, 0);
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedSmallVehicleAndDriverIsNormal_ShouldReturnDetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("2 Vehicle{color='BLACK', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot);
        parkingLot.setCapacity(3);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.NORMAL, 2);
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedNoSmallVehicleAndDriverIsHandicap_ShouldReturnEmptyList() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        expectedList.add(lot);
        parkingLot.setCapacity(3);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.HANDICAP, 1);
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenSmallCarAndDriverIsHandicapToPark_WhenMultipleParkingLotToParkVehicle_ShouldReturndetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        List<String> lot2 = new ArrayList<>();
        lot2.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Ritesh'}");
        expectedList.add(lot1);
        expectedList.add(lot2);

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Ritesh");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        Vehicle vehicle3 = new Vehicle("BLACK", "BMW", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.HANDICAP, 0);
        Assert.assertEquals(expectedList, vehicleList);
    }
    //UC17
    @Test
    public void givenParkingLotSystem_WhenParkedVehicle_ShouldReturnDetailsOfAllVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        lot.add("1 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot);
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findAllVehicle();
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenCarToPark_WhenMultipleParkingLotToParkVehicle_ShouldReturndetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        List<String> lot2 = new ArrayList<>();
        lot1.add("0 Vehicle{color='BLUE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        lot2.add("0 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Ritu'}");
        expectedList.add(lot1);
        expectedList.add(lot2);

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLotSystem.addLot(parkingLot2);
        Vehicle vehicle1 = new Vehicle("BLUE", "BMW", "MH-12-1234", "Shamal");
        Vehicle vehicle2 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Ritu");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.HANDICAP, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(vehicle2, DriverType.HANDICAP, VehicleSize.SMALL);
        List<List<String>> vehicleList = parkingLotSystem.findAllVehicle();
        Assert.assertEquals(expectedList, vehicleList);
    }
}