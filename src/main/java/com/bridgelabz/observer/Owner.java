package com.bridgelabz.observer;

import com.bridgelabz.utility.Attendant;

import java.util.List;

public class Owner implements IObservable {
    private List<Attendant> attendants;
    private String parkingLotStatus;
    private int numberOfSlot;

    public Owner(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    @Override
    public void update(Object status) {
        this.setParkingLotStatus((String) status);
    }

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    public void setParkingLotStatus(String isFull) {
        this.parkingLotStatus = isFull;
    }

    public void assignAttendantForParkingLot(int numberOfSlot) {
        this.numberOfSlot = numberOfSlot;
        for (int i = 0; i < numberOfSlot; i++) {
            attendants.add(new Attendant("Attendant" + Integer.toString(i + 1)));
        }
    }

    public Attendant getAttendant() {
        return attendants.get((int) (Math.random() * numberOfSlot));
    }
}
