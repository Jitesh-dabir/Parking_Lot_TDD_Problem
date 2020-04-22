package com.bridgelabz.observer;

public class Owner implements IObservable {
    private String parkingLotStatus;

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
}