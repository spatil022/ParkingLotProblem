package com.bridgelabz.Exception;

public class ParkingLotSystemException extends RuntimeException{

    private ExceptionType type;

    public enum ExceptionType {
        VEHICLE_NOT_FOUND, PARKING_FULL, VEHICLE_ALREADY_PARKED;
    }

    public ParkingLotSystemException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
