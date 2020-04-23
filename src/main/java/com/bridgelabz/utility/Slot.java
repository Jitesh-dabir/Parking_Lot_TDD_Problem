package com.bridgelabz.utility;

import java.time.LocalDateTime;

public class Slot {
    private Attendant attendant;
    private Vehicle vehicle;
    private LocalDateTime time;

    public Slot(Vehicle vehicle, Attendant attendant) {
        this.vehicle = vehicle;
        this.attendant = attendant;
        this.time = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public Slot resetSlot() {
        this.vehicle = null;
        this.attendant = null;
        this.time = null;
        return this;
    }
}
