package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.IObservable;
import com.bridgelabz.observer.Owner;
import com.bridgelabz.utility.ParkingLot;
import com.bridgelabz.utility.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    private String isFull;
    private int parkingLotCapacity = 11;
    private int numberOfSlot = 4;
    LinkedHashMap<String, Vehicle> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;
    String lotName[] = new String[25];
    private List<IObservable> observableList = new ArrayList<>();
    Map<String,Vehicle> listForPoliceDepartment = new HashMap<>();
    Owner owner = null;
    ParkingLot parkingLotHandler = null;

    public ParkingLotSystem(Owner owner, ParkingLot parkingLotHandler, LinkedHashMap parkingLot, LinkedHashMap availableLot) {
        this.owner = owner;
        this.parkingLotHandler = parkingLotHandler;
        this.parkingLot = parkingLot;
        this.availableLot = availableLot;
    }

    public void addObserver(IObservable iObservable) {
        this.observableList.add(iObservable);
    }

    public void setStatus(String isFull) {
        this.isFull = isFull;
        for (IObservable observable : this.observableList) {
            observable.update(this.isFull);
        }
    }

    public void park(Vehicle vehicle) throws ParkingLotException {
        parkingLotHandler.park(parkingLot, vehicle, availableLot);
        if (!parkingLot.containsValue(null))
            setStatus("Full");
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        parkingLotHandler.UnPark(parkingLot, vehicle, availableLot);
        if (parkingLot.containsValue(null)) {
            setStatus("Have Space");
        }
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        if (parkingLot.containsValue(vehicle))
            return true;
        return false;
    }

    public boolean isVehicleUnParked(Vehicle vehicle) {
        if (!parkingLot.containsValue(vehicle))
            return true;
        return false;
    }

    public String getMyCarParkingNumber(Vehicle vehicle) {
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (parkingLot.get(key) == vehicle)
                return key;
        }
        return null;
    }

    public void createParkingLot() {
        int counter = 1, index = 0, slot = 1, length = 0, slotCapacity = 0;
        String letters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        lotName = letters.split(" ");
        while (index != parkingLotCapacity) {
            slotCapacity = parkingLotCapacity / numberOfSlot;
            if (parkingLotCapacity % numberOfSlot != 0) {
                slotCapacity += 1;
            }
            if (counter == slotCapacity + 1) {
                availableLot.put(lotName[slot - 1], counter - 1);
                counter = 1;
                slot++;
            }
            String number1 = Integer.toString(counter);
            length = number1.length();
            if (length == 1) {
                number1 = "0" + number1;
            }
            String key = lotName[slot - 1] + number1;
            parkingLot.put(key, null);
            index++;
            counter++;
            if (slot == numberOfSlot)
                availableLot.put(lotName[slot - 1], counter - 1);
        }
    }

    public Map<String, Vehicle> getRecordsByVehicleColorForPolice(Vehicle.Color color) {
        return listForPoliceDepartment = parkingLot.entrySet().stream().filter(entry -> color.equals(entry.getValue().getColor()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}