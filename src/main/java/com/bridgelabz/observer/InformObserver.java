package com.bridgelabz.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InformObserver {
    private List<ParkingLotSubscriber> observersList;
    private boolean parkingCapacity;
    private int time;

    public InformObserver() {
        this.observersList = new ArrayList<>();
    }

    public void subscribeParkingLotObserver(ParkingLotSubscriber parkingLotSubscriber) {
        this.observersList.add(parkingLotSubscriber);
    }

    public void unsubscribeParkingLotObserver(ParkingLotSubscriber parkingLotSubscriber) {
        this.observersList.remove(parkingLotSubscriber);
    }

    public void parkingFull() {
        this.parkingCapacity = true;
        isParkingFull();
    }

    public void parkingAvailable() {
        this.parkingCapacity = false;
        isParkingFull();
    }

    public void isParkingFull() {
        for (ParkingLotSubscriber parkingLotSubscriber : observersList)
            parkingLotSubscriber.parkingFull(this.parkingCapacity);
    }

    public void setParkingTime(int parkingTime) {
        this.time = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()) - parkingTime;
        for (ParkingLotSubscriber parkingLotSubscriber : observersList)
            parkingLotSubscriber.parkingTime(time);
    }
}