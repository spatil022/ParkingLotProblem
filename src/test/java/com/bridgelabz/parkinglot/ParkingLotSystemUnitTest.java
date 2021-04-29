package com.bridgelabz.parkinglot;

import com.bridgelabz.Exception.ParkingLotSystemException;
import com.bridgelabz.enums.DriverType;
import com.bridgelabz.enums.VehicleSize;
import com.bridgelabz.model.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}