package com.bridgelabz.utility;

public class Vehicle {

    private Color color;
    private Driver driver;
    private String vehicleId;
    private VehicleType vehicleType;
    private Vehicle_Brand vehicle_brand;

    public Vehicle() {
    }

    public enum VehicleType {
        SMALL, LARGE
    }

    public enum Vehicle_Brand {
        TOYOTA, BMW, MARUTI, CIAZ, HONDA
    }

    public enum Color {
        RED, BLUE, BLACK, GREEN, WHITE
    }

    public Vehicle(String id, Vehicle_Brand vehicle_brand, Driver driver, VehicleType vehicleType, Color color) {
        this.vehicleId = id;
        this.driver = driver;
        this.vehicleType = vehicleType;
        this.color = color;
        this.vehicle_brand = vehicle_brand;
    }

    public Driver getDriver() {
        return driver;
    }

    public Color getColor() {
        return color;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle_Brand getVehicle_brand() {
        return vehicle_brand;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}