package com.bridgelabz.model;

import com.bridgelabz.observer.ParkingLotSubscriber;

public class AirportSecurity implements ParkingLotSubscriber {

    private boolean parkingCapacity;
    private int parkingTime;

    @Override
    public void parkingFull(boolean parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    @Override
    public boolean isParkingFull() {
        return this.parkingCapacity;
    }

    @Override
    public void parkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }

    @Override
    public int getParkingTime() {
        return parkingTime;
    }
}