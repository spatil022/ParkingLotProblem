package com.bridgelabz.parkinglot;

import com.bridgelabz.Exception.ParkingLotSystemException;
import com.bridgelabz.enums.DriverType;
import com.bridgelabz.enums.VehicleSize;
import com.bridgelabz.model.Vehicle;
import com.bridgelabz.observer.InformObserver;
import com.bridgelabz.observer.ParkingLotSubscriber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    private List<ParkingLot> parkingLotList;
    private InformObserver informObserver;
    private ParkingLot parkingLot;

    public ParkingLotSystem() {
        this.informObserver = new InformObserver();
        this.parkingLotList = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLot parkingLot) {
        if(this.parkingLotList.contains(parkingLot)){
            return true;
        }
        return false;
    }

    public void parkVehicle(Vehicle vehicle, DriverType driverType, VehicleSize vehicleSize) {
        parkingLot = getParkingLotHavingMaxSpace();
        if (parkingLot.isParkingFull()) {
            throw new ParkingLotSystemException("Parking Is Full", ParkingLotSystemException.ExceptionType.PARKING_FULL);
        }
        parkingLot.parkVehicle(vehicle, driverType, vehicleSize);
        if (parkingLot.isParkingFull()) {
            informObserver.parkingFull();
        }
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList) {
            if (parkingLot.isVehicleParked(vehicle)) {
                parkingLot.unparkVehicle(vehicle);
                informObserver.parkingAvailable();
                return true;
            }
        }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    private ParkingLot getParkingLotHavingMaxSpace() {
        return parkingLotList.stream()
                .sorted(Comparator.comparing(list -> list.getListOfEmptyParkingSlots().size(), Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    public int findVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLotList)
            if (parkingLot.isVehicleParked(vehicle))
                return parkingLot.findVehicle(vehicle);
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void getVehicleParkingTime(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList)
            if (parkingLot.isVehicleParked(vehicle)) {
                informObserver.setParkingTime(parkingLot.getVehicleParkingTime(vehicle));
                return;
            }
        throw new ParkingLotSystemException("Vehicle Is Not Available", ParkingLotSystemException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public void subscribe(ParkingLotSubscriber subscriber) {
        informObserver.subscribeParkingLotObserver(subscriber);
    }

    public void unsubscribe(ParkingLotSubscriber subscriber) {
        informObserver.unsubscribeParkingLotObserver(subscriber);
    }

    public List<List<Integer>> findVehicleByColor(String color) {
        List<List<Integer>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByColor(color))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findVehicleByColorAndModel(String color, String model) {
        List<List<String>> vehicleListByColorAndModel = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByColorAndModel(color, model))
                .collect(Collectors.toList());
        return vehicleListByColorAndModel;
    }

    public List<List<String>> findVehicleByModel(String model) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModel(model))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findVehicleByTime(int parkedTime) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByTime(parkedTime))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findVehicleBySizeDriverAndSlot(VehicleSize vehicleSize, DriverType driverType, int slot) {
        List<List<String>> vehicleListByColor = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findBySizeDriverAndSlot(vehicleSize, driverType, slot))
                .collect(Collectors.toList());
        return vehicleListByColor;
    }

    public List<List<String>> findAllVehicle() {
        List<List<String>> allVehicleList = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findAllVehicle())
                .collect(Collectors.toList());
        return allVehicleList;
    }
}