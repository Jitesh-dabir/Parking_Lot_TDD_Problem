package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.IObservable;
import com.bridgelabz.observer.Owner;
import com.bridgelabz.utility.ParkingLot;
import com.bridgelabz.utility.Slot;
import com.bridgelabz.utility.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    Owner owner = null;
    private String isFull;
    String lotName[] = new String[25];
    ParkingLot parkingLotHandler = null;
    Vehicle vehicle = new Vehicle();
    Slot slot = new Slot(null, null);
    LinkedHashMap<String, Slot> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;
    private List<IObservable> observableList = new ArrayList<>();
    Map<String, Slot> listForPoliceDepartment = new HashMap<>();

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
        if (isParkingLotFull()) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        parkingLotHandler.park(parkingLot, vehicle, availableLot, slot, owner);
        if (isParkingLotFull())
            setStatus("Full");
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null) {
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        }
        parkingLotHandler.UnPark(parkingLot, vehicle, availableLot, slot);
        if (!isParkingLotFull()) {
            setStatus("Have Space");
        }
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        Iterator it = parkingLot.keySet().iterator();
        while (it.hasNext()) {
            if ((parkingLot.get(it.next()).getVehicle()) == vehicle)
                return true;
        }
        return false;
    }

    public boolean isVehicleUnParked(Vehicle vehicle) {
        Iterator it = parkingLot.keySet().iterator();
        while (it.hasNext()) {
            if (((parkingLot.get(it.next()).getVehicle()) == vehicle))
                return false;
        }
        return true;
    }

    public boolean isParkingLotFull() {
        Iterator it = parkingLot.keySet().iterator();
        while (it.hasNext()) {
            if ((parkingLot.get(it.next()).getVehicle()) == null)
                return false;
        }
        return true;
    }

    public String getMyCarParkingNumber(Vehicle vehicle) {
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (vehicle.equals(parkingLot.get(key).getVehicle()))
                return key;
        }
        return null;
    }

    public void createParkingLot(int parkingLotCapacity, int numberOfSlot) {
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
            parkingLot.put(key, this.slot);
            index++;
            counter++;
            if (slot == numberOfSlot)
                availableLot.put(lotName[slot - 1], counter - 1);
        }
        owner.assignAttendantForParkingLot(numberOfSlot, availableLot);
    }

    public Map<String, Slot> getRecordsByVehicleColorForPolice(Vehicle.Color color) {
        return parkingLot.entrySet().stream().filter(entry -> color.equals(entry.getValue().getVehicle().getColor()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Slot> getRecordsByVehicleColorAndName(Vehicle.Vehicle_Brand brand, Vehicle.Color color) {
        return parkingLot.entrySet().stream().filter(entry -> color.equals(entry.getValue().getVehicle().getColor()) && brand.equals(entry.getValue().getVehicle().getVehicle_brand()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Slot> getRecordsByCarBrand(Vehicle.Vehicle_Brand brand) {
        return parkingLot.entrySet().stream().filter(entry -> brand.equals(entry.getValue().getVehicle().getVehicle_brand()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}