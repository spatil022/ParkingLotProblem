package com.bridgelabz.parkinglot;

import com.bridgelabz.Exception.ParkingLotSystemException;
import com.bridgelabz.enums.DriverType;
import com.bridgelabz.enums.VehicleSize;
import com.bridgelabz.model.ParkingSlot;
import com.bridgelabz.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private int parkingCapacity;
    private List<ParkingSlot> vehiclesList;
    private ParkingSlot parkingSlot;
    private int emptyParkingSlot;

    public ParkingLot(int parkingCapacity) {
        setCapacity(parkingCapacity);
    }

    public int setCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        initializeParkingLot();
        return vehiclesList.size();
    }

    public void initializeParkingLot() {
        this.vehiclesList = new ArrayList();
        IntStream.range(0, this.parkingCapacity)
                .forEach(slots -> vehiclesList.add(null));
    }

    public List<Integer> getListOfEmptyParkingSlots() {
        List<Integer> emptyParkingSlotList = new ArrayList<>();
        IntStream.range(0, this.parkingCapacity)
                .filter(slot -> vehiclesList.get(slot) == null)
                .forEach(emptyParkingSlotList::add);
        return emptyParkingSlotList;
    }

    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotSystemException("Vehicle Already Parked", ParkingLotSystemException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        emptyParkingSlot = getEmptyParkingSlotListBasedOnDriverType(driverType);
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot, vehicleSize, driverType);
        this.vehiclesList.set(emptyParkingSlot, parkingSlot);
    }

    private Integer getEmptyParkingSlotListBasedOnDriverType(DriverType driverType) {
        List<Integer> emptySlots = getListOfEmptyParkingSlots().stream()
                .sorted(driverType.order)
                .collect(Collectors.toList());
        return emptySlots.get(0);
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        VehicleSize vehicleSize = null;
        DriverType driverType = null;
        parkingSlot = new ParkingSlot(vehicle, emptyParkingSlot, vehicleSize, driverType);
        if (vehiclesList.contains(parkingSlot)) {
            return true;
        }
        return false;
    }

    public boolean unparkVehicle(Object vehicle) {
        int slot = findVehicle(vehicle);
        vehiclesList.set(slot, null);
        return true;
    }

    public int findVehicle(Object vehicle) {
        return vehiclesList.indexOf(parkingSlot);
    }

    public int getVehicleParkingTime(Object vehicle) {
        return parkingSlot.time;
    }

    public boolean isParkingFull() {
        if (this.parkingCapacity == this.vehiclesList.size() && !vehiclesList.contains(null)) {
            return true;
        }
        return false;
    }

    public List<Integer> findByColor(String color) {
        List<Integer> vehicleListByColor = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equalsIgnoreCase(color))
                .map(parkingSlot -> parkingSlot.getLocation())
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<String> findByColorAndModel(String color, String model) {
        List<String> vehicleListByColorAndModel = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getColor().equalsIgnoreCase(color))
                .filter(parkingSlot -> parkingSlot.getVehicle().getModel().equalsIgnoreCase(model))
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByColorAndModel;
    }

    public List<String> findByModel(String model) {
        List<String> vehicleListByModel = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.getVehicle().getModel().equalsIgnoreCase(model))
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByModel;
    }

    public List<String> findByTime(int parkedTime) {
        List<String> vehicleListByTime = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> (parkingSlot.time - (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())) < parkedTime)
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByTime;
    }

    public List<String> findBySizeDriverAndSlot(VehicleSize vehicleSize, DriverType driverType, int slot) {
        List<String> vehicleListByTime = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .filter(parkingSlot -> parkingSlot.vehicleSize.equals(vehicleSize))
                .filter(parkingSlot -> parkingSlot.driverType.equals(driverType))
                .filter(parkingSlot -> parkingSlot.getLocation() == slot )
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return vehicleListByTime;
    }

    public List<String> findAllVehicle() {
        List<String> allVehicleList = this.vehiclesList.stream()
                .filter(parkingSlot -> parkingSlot.getVehicle() != null)
                .map(parkingSlot -> parkingSlot.getLocation() + " " + parkingSlot.getVehicle())
                .collect(Collectors.toList());
        return allVehicleList;
    }
}
