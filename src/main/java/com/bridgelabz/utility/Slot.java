package com.bridgelabz.utility;

import java.time.LocalTime;

public class Slot {
    private Attendant attendant;
    private Vehicle vehicle;
    private LocalTime time;

    public Slot(Vehicle vehicle, Attendant attendant) {
        this.vehicle = vehicle;
        this.attendant = attendant;
        this.time = LocalTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Slot resetSlot() {
        this.vehicle = null;
        this.attendant = null;
        this.time = null;
        return this;
    }
}
