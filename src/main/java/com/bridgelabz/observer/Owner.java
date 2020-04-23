package com.bridgelabz.observer;

import com.bridgelabz.utility.Attendant;

import java.util.LinkedHashMap;
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

    public void assignAttendantForParkingLot(int numberOfSlot, LinkedHashMap<String, Integer> availableLot) {
        this.numberOfSlot = numberOfSlot;
        for (int i = 0; i < numberOfSlot; i++) {
            String firstKey = availableLot.keySet().toArray()[i].toString();
            attendants.add(new Attendant("attendant_" + firstKey));
        }
    }

    public Attendant getAttendant(String keyAvailable) {
        for (int i = 0; i < attendants.size(); i++) {
            if (attendants.get(i).getName().contains(keyAvailable))
                return attendants.get(i);
        }
        return null;
    }
}
