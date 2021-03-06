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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotSystemUnitTest {
    private ParkingLot parkingLot;
    static Vehicle vehicle;
    private ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        parkingLot = mock(ParkingLot.class);
        vehicle = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.addLot(parkingLot);
    }

    @Test
    public void givenParkingLotSystem_WhenAddedLots_ShouldReturnTrue() {
        parkingLotSystem.addLot(parkingLot);
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenNotAddedLots_ShouldReturnFalse() {
        ParkingLot parkingLot1 = mock(ParkingLot.class);
        boolean isLotAdded = parkingLotSystem.isLotAdded(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingLot).isVehicleParked(vehicle)).thenReturn(true);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isVehicleParked);
    }

    @Test
    public void givenVehicleToPark_WhenAlreadyParked_ShouldReturnException() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            when((parkingLot).isVehicleParked(vehicle)).thenThrow(new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED));
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Already Parked", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            when((parkingLot).isVehicleParked(vehicle)).thenReturn(false);
            parkingLotSystem.isVehicleParked(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFull_ShouldThrowException() {
        try {
            when((parkingLot).isParkingFull()).thenReturn(true);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenUnparkVehicle_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingLot).isVehicleParked(vehicle)).thenReturn(true);
        boolean isUnparkVehicle = parkingLotSystem.unparkVehicle(vehicle);
        Assert.assertTrue(isUnparkVehicle);
    }

    @Test
    public void givenVehicleParkedAndAnotherVehicle_WhenUnparkVehicle_ShouldReturnException() {
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            when((parkingLot).isVehicleParked(vehicle)).thenReturn(false);
            parkingLotSystem.unparkVehicle(vehicle1);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    //ParkingOwner Class Mocked To Return True
    @Test
    public void givenVehicle_WhenParkingFull_ShouldInformOwner() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        parkingLotSystem.subscribe(parkingOwner);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingOwner).isParkingFull()).thenReturn(true);
        Assert.assertTrue(parkingOwner.isParkingFull());
    }

    @Test
    public void givenVehicle_WhenParkingFullAndOwnerIsObserver_ShouldInformOwner() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        try {
            parkingLotSystem.subscribe(parkingOwner);
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            when((parkingLot).isParkingFull()).thenReturn(true);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Parking Is Full", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenParkingFull_ShouldNotInformOwner() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        parkingLotSystem.subscribe(parkingOwner);
        parkingLotSystem.unsubscribe(parkingOwner);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingOwner).isParkingFull()).thenReturn(true);
        Assert.assertTrue(parkingOwner.isParkingFull());
    }

    @Test
    public void givenCapacityIs2_ShouldBeAbleToPark2Vehicle() {
        Vehicle vehicle1 = new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingLot).isVehicleParked(vehicle)).thenReturn(true);
        boolean isParked1 = parkingLot.isVehicleParked(vehicle);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        when((parkingLot).isVehicleParked(vehicle1)).thenReturn(true);
        boolean isParked2 = parkingLot.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }

    @Test
    public void givenSameVehiclesTwoTimes_WhenParked_ShouldThrowException() {
        parkingLot.setCapacity(2);
        try {
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
            when((parkingLot).isVehicleParked(vehicle)).thenThrow(new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED));
            parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Already Parked", e.getMessage());
        }
    }

    //AirportSecurity Class Mocked To Return True
    @Test
    public void givenVehicle_WhenParkingLotFull_ShouldInformToAirportSecurity() {
        AirportSecurity airportSecurity = mock(AirportSecurity.class);
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.LARGE);
        when(airportSecurity.isParkingFull()).thenReturn(true);
        boolean parkingFull = airportSecurity.isParkingFull();
        Assert.assertTrue(parkingFull);
    }

    //ParkingOwner Class Mocked To Return False
    @Test
    public void givenVehicle_WhenSpaceIsAvailable_ShouldInformOwner() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        parkingLotSystem.subscribe(parkingOwner);
        try {
            parkingLot.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.LARGE);
            parkingLot.parkVehicle(new Vehicle("WHITE", "TOYOTA", "MH-12-1234", "Shamal"), DriverType.NORMAL, VehicleSize.SMALL);
        } catch (ParkingLotSystemException e) {
            when(parkingOwner.isParkingFull()).thenReturn(false);
            boolean parkingAvailable = parkingOwner.isParkingFull();
            Assert.assertFalse(parkingAvailable);
        }
    }

    //AirportSecurity Class Mocked To Return True
    @Test
    public void givenVehicle_WhenSpaceIsAvailable_ShouldInformToAirportSecurity() {
        AirportSecurity airportSecurity = mock(AirportSecurity.class);
        parkingLotSystem.subscribe(airportSecurity);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when(airportSecurity.isParkingFull()).thenReturn(false);
        boolean parkingAvailable = airportSecurity.isParkingFull();
        Assert.assertFalse(parkingAvailable);

    }

    //ParkingOwner And AirportSecurity Class Mocked To Return True
    @Test
    public void givenVehicle_WhenSpaceIsAvailable_ShouldInformToParkingLotOwnerAndAirportSecurity() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        AirportSecurity airportSecurity = mock(AirportSecurity.class);
        parkingLotSystem.subscribe(parkingOwner);
        parkingLotSystem.subscribe(airportSecurity);
        parkingLot.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingOwner.isParkingFull()).thenReturn(false);
        when(airportSecurity.isParkingFull()).thenReturn(false);
        boolean parkingAvailable1 = parkingOwner.isParkingFull();
        boolean parkingAvailable2 = airportSecurity.isParkingFull();
        Assert.assertFalse(parkingAvailable1 && parkingAvailable2);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleFound_ShouldReturnVehicleSlot() {
        when((parkingLot).isVehicleParked(vehicle)).thenReturn(true);
        when((parkingLot).findVehicle(vehicle)).thenReturn(0);
        int slotNumber = parkingLotSystem.findVehicle(vehicle);
        Assert.assertEquals(0, slotNumber);
    }

    @Test
    public void givenParkingLotSystem_WhenVehicleNotFound_ShouldReturnException() {
        try {
            parkingLotSystem.findVehicle(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndOwnerIsObserver_ShouldInformParkingTimeToOwner() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        parkingLotSystem.subscribe(parkingOwner);
        when((parkingLot).isVehicleParked(vehicle)).thenReturn(true);
        when(parkingLot.getVehicleParkingTime(vehicle)).thenReturn(0);
        parkingLotSystem.getVehicleParkingTime(vehicle);
        when(parkingOwner.getParkingTime()).thenReturn(0);
        int parkingTime = parkingOwner.getParkingTime();
        Assert.assertEquals(0, parkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleNotParkedAndOwnerIsObserver_ShouldReturnException() {
        ParkingOwner parkingOwner = mock(ParkingOwner.class);
        parkingLotSystem.subscribe(parkingOwner);
        try {
            when((parkingLot).isVehicleParked(vehicle)).thenReturn(false);
            parkingLotSystem.getVehicleParkingTime(vehicle);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenWhiteCarToPark_whenParkingLotToParkVehicle_shouldReturnLocationOfAllWhiteCars() {
        List<List<Integer>> expectedList = new ArrayList<>();
        List<Integer> lot1 = new ArrayList<>();
        lot1.add(0);
        expectedList.add(lot1);
        parkingLot.setCapacity(2);
        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingLot.isVehicleParked(vehicle1)).thenReturn(true);
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingLot.findByColor("WHITE")).thenReturn(lot1);
        List<List<Integer>> vehicleByColor = parkingLotSystem.findVehicleByColor("WHITE");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedBlueToyotaCar_ShouldReturnLocationAndAttendantNameAndPlateNumber() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> carList = new ArrayList();
        carList.add("1 Vehicle{color='BLUE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(carList);
        Vehicle vehicle1 = new Vehicle("BLUE", "TOYOTA", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingLot.findByColorAndModel("BLUE", "TOYOTA")).thenReturn(carList);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleByColorAndModel("BLUE", "TOYOTA");
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenBMWCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllBMWCars() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingLot.findByModel("BMW")).thenReturn(lot1);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByModel("BMW");
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenCarToPark_WhenParkingLotToParkVehicle_ShouldReturnLocationOfAllCarsWithin30Minutes() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot1 = new ArrayList<>();
        lot1.add("1 Vehicle{color='WHITE', model='BMW', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot1);

        parkingLot.setCapacity(2);
        Vehicle vehicle = new Vehicle("WHITE", "BMW", "MH-12-1234", "Shamal");
        parkingLotSystem.parkVehicle(vehicle, DriverType.NORMAL, VehicleSize.SMALL);
        when(parkingLot.findByTime(30)).thenReturn(lot1);
        List<List<String>> vehicleByColor = parkingLotSystem.findVehicleByTime(30);
        Assert.assertEquals(expectedList, vehicleByColor);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedSmallVehicleAndDriverIsHandicap_ShouldReturnDetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("1 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot);
        parkingLot.setCapacity(3);

        when(parkingLot.findBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.HANDICAP, 1)).thenReturn(lot);
        List<List<String>> vehicleList = parkingLotSystem.findVehicleBySizeDriverAndSlot(VehicleSize.SMALL, DriverType.HANDICAP, 1);
        Assert.assertEquals(expectedList, vehicleList);
    }

    @Test
    public void givenParkingLotSystem_WhenParkedVehicle_ShouldReturnDetailsOfVehicle() {
        List<List<String>> expectedList = new ArrayList<>();
        List<String> lot = new ArrayList();
        lot.add("0 Vehicle{color='WHITE', model='TOYOTA', numberPlate='MH-12-1234', attender='Shamal'}");
        expectedList.add(lot);
        parkingLot.setCapacity(3);

        when(parkingLot.findAllVehicle()).thenReturn(lot);
        List<List<String>> vehicleList = parkingLotSystem.findAllVehicle();
        Assert.assertEquals(expectedList, vehicleList);
    }
}