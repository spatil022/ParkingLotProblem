package com.bridgelabz.model;

import com.bridgelabz.enums.DriverType;
import com.bridgelabz.enums.VehicleSize;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ParkingSlot {
    public int time;
    public Vehicle vehicle;
    public VehicleSize vehicleSize;
    public DriverType driverType;
    private Integer slot;

    public ParkingSlot(Vehicle vehicle, int slot, VehicleSize vehicleSize, DriverType driverType) {
        this.vehicle = vehicle;
        this.time = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
        this.vehicleSize = vehicleSize;
        this.driverType = driverType;
        this.slot = slot;
    }

    public int getLocation() {
        return this.slot;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(vehicle, that.vehicle);
    }
}
