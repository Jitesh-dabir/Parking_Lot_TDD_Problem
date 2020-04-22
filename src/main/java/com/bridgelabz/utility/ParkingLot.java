package com.bridgelabz.utility;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.utility.Driver;

import java.util.*;

public class Attendant {
    private String vehicleKey;
    String parkMethod = "PARK";
    String unParkMethod = "UN_PARK";
    String parkMethodForLargeVehicle = "PARK_LARGE_VEHICLE";
    String unParkMethodForLargeVehicle = "UNPARK_LARGE_VEHICLE";
    int value;

    public void park(HashMap<String, Vehicle> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot) throws ParkingLotException {
        if (!parkingLot.containsValue(null)) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        String keyAvailable = Collections.max(availableLot.entrySet(), Map.Entry.comparingByValue()).getKey();
        Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            vehicleKey = parkingLotItr.next();
            if (vehicle.getDriver().getDriverType().equals(Driver.DriverType.HANDICAP) && parkingLot.get(vehicleKey) == null) {
                parkingLot.replace(vehicleKey, vehicle);
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            } else if (vehicleKey.substring(0, 1).equals(keyAvailable) && parkingLot.get(vehicleKey) == null && !vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE)) {
                parkingLot.replace(vehicleKey, vehicle);
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            }
            String nextVehicleKey = getNextKey(vehicleKey, parkingLot);
            if (vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE) && parkingLot.get(vehicleKey) == null && parkingLot.get(vehicleKey) == parkingLot.get(nextVehicleKey)) {
                parkingLot.replace(vehicleKey, vehicle);
                parkingLot.replace(nextVehicleKey, vehicle);
                updateAvailableLot(availableLot, vehicleKey, parkMethodForLargeVehicle);
                break;
            }
        }
    }

    public String getNextKey(String vehicleKey, HashMap<String, Vehicle> parkingLot) throws ParkingLotException {
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
            }  else if (vehicleKey.substring(0, 1).equals(availableKey) && method == unParkMethodForLargeVehicle) {
                value = (availableLot.get(availableKey) + 2);
                availableLot.replace(availableKey, value);
            }
        }
    }

    public void UnPark(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot) throws ParkingLotException {
        if (vehicle == null) {
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        }
        Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            String key = parkingLotItr.next();
            if (parkingLot.get(key) == vehicle) {
                vehicleKey = key;
                break;
            }
        }
        String nextVehicleKey = getNextKey(vehicleKey, parkingLot);
        if (vehicle.getVehicleType().equals(Vehicle.VehicleType.LARGE)) {
            parkingLot.replace(vehicleKey, null);
            parkingLot.replace(nextVehicleKey, null);
            updateAvailableLot(availableLot, vehicleKey, unParkMethodForLargeVehicle);
        } else {
            parkingLot.replace(vehicleKey, null);
            updateAvailableLot(availableLot, vehicleKey, unParkMethod);
        }
    }
}
