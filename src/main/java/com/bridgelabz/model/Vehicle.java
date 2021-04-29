package com.bridgelabz.model;

public class Vehicle {
    public String model;
    public String numberPlate;
    public String attender;
    public String color;

    public Vehicle(String color, String model, String numberPlate, String attender) {
        this.color = color;
        this.model = model;
        this.numberPlate = numberPlate;
        this.attender = attender;
    }

    public String getColor() {
        return this.color;
    }

    public String getModel() {
        return this.model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", attender='" + attender + '\'' +
                '}';
    }
}
