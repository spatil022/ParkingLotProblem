package com.bridgelabz.parkinglot;

import com.bridgelabz.model.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

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

}