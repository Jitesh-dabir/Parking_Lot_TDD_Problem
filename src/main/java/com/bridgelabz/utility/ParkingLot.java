package com.bridgelabz.utility;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.Owner;
import com.bridgelabz.utility.Driver;

import java.util.*;

public class ParkingLot {
    private String vehicleKey;
    String parkMethod = "PARK";
    String unParkMethod = "UN_PARK";
    String nextVehicleKey;
    String parkMethodForLargeVehicle = "PARK_LARGE_VEHICLE";
    String unParkMethodForLargeVehicle = "UN_PARK_LARGE_VEHICLE";
    int value;

    public void park(HashMap<String, Slot> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot, Slot slot, Owner owner) throws ParkingLotException {
        String keyAvailable = Collections.max(availableLot.entrySet(), Map.Entry.comparingByValue()).getKey();
        Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            vehicleKey = parkingLotItr.next();
            if (vehicle.getDriver().getDriverType().equals(Driver.DriverType.HANDICAP) && parkingLot.get(vehicleKey).getVehicle() == null) {
                parkingLot.replace(vehicleKey, new Slot(vehicle, owner.getAttendant()));
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            } else if (vehicleKey.substring(0, 1).equals(keyAvailable) && parkingLot.get(vehicleKey).getVehicle() == null && !vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE)) {
                parkingLot.replace(vehicleKey, new Slot(vehicle, owner.getAttendant()));
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            }
            String nextVehicleKey = getNextKey(vehicleKey, parkingLot);
            if (vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE) && parkingLot.get(vehicleKey).getVehicle() == null && parkingLot.get(vehicleKey).getVehicle() == parkingLot.get(nextVehicleKey).getVehicle()) {
                parkingLot.replace(vehicleKey, new Slot(vehicle, owner.getAttendant()));
                parkingLot.replace(nextVehicleKey, new Slot(vehicle, owner.getAttendant()));
                updateAvailableLot(availableLot, vehicleKey, parkMethodForLargeVehicle);
                break;
            }
        }
    }

    public String getNextKey(String vehicleKey, HashMap<String, Slot> parkingLot) throws ParkingLotException {
        try {
            Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
            while (parkingLotItr.hasNext()) {
                String key = parkingLotItr.next();
                if (key.equals(vehicleKey))
                    return parkingLotItr.next();
            }
        } catch (NoSuchElementException e) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL_FOR_LARGE_VEHICLE, "Parking lot full for large vehicle");
        }

        return null;
    }

    public void updateAvailableLot(LinkedHashMap<String, Integer> availableLot, String vehicleKey, String method) {
        Iterator<String> availableLotItr = availableLot.keySet().iterator();
        while (availableLotItr.hasNext()) {
            String availableKey = availableLotItr.next();
            if (vehicleKey.substring(0, 1).equals(availableKey) && method == parkMethod) {
                value = (availableLot.get(availableKey) - 1);
                availableLot.replace(availableKey, value);
                break;
            } else if (vehicleKey.substring(0, 1).equals(availableKey) && method == unParkMethod) {
                value = (availableLot.get(availableKey) + 1);
                availableLot.replace(availableKey, value);
                break;
            } else if (vehicleKey.substring(0, 1).equals(availableKey) && method == parkMethodForLargeVehicle) {
                value = (availableLot.get(availableKey) - 2);
                availableLot.replace(availableKey, value);
            } else if (vehicleKey.substring(0, 1).equals(availableKey) && method == unParkMethodForLargeVehicle) {
                value = (availableLot.get(availableKey) + 2);
                availableLot.replace(availableKey, value);
            }
        }
    }

    public void UnPark(LinkedHashMap<String, Slot> parkingLot1, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot, Slot slot) throws ParkingLotException {
        Iterator<String> parkingLotItr = parkingLot1.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            String key = parkingLotItr.next();
            if (parkingLot1.get(key).getVehicle().equals(vehicle)) {
                vehicleKey = key;
                break;
            }
        }
        if (vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE) && !vehicleKey.equals(getLastKey(parkingLot1))) {
            nextVehicleKey = getNextKey(vehicleKey, parkingLot1);
            parkingLot1.replace(vehicleKey, slot.resetSlot());
            parkingLot1.replace(nextVehicleKey, slot.resetSlot());
            updateAvailableLot(availableLot, vehicleKey, unParkMethodForLargeVehicle);
        } else {
            parkingLot1.replace(vehicleKey, slot.resetSlot());
            updateAvailableLot(availableLot, vehicleKey, unParkMethod);
        }
    }

    private String getLastKey(LinkedHashMap<String, Slot> parkingLot) {
        for (Map.Entry<String, Slot> entry : parkingLot.entrySet()) {
            String lastKey = entry.getKey();
            return lastKey;
        }
        return null;
    }
}
