import java.util.*;

public class Attendant {
    private String vehicleKey;
    String parkMethod = "PARK";
    String unParkMethod = "UN_PARK";
    int value;

    public void park(HashMap<String, Vehicle> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot) {
        String keyAvailable = Collections.max(availableLot.entrySet(), Map.Entry.comparingByValue()).getKey();
        Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            vehicleKey = parkingLotItr.next();
            if (vehicle.getDriverType().equals(Vehicle.DriverType.HANDICAP) && parkingLot.get(vehicleKey) == null) {
                parkingLot.replace(vehicleKey, vehicle);
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            } else if (vehicleKey.substring(0, 2).equals(keyAvailable) && parkingLot.get(vehicleKey) == null) {
                parkingLot.replace(vehicleKey, vehicle);
                updateAvailableLot(availableLot, vehicleKey, parkMethod);
                break;
            }
        }
    }

    public void updateAvailableLot(LinkedHashMap<String, Integer> availableLot, String vehicleKey, String method) {
        Iterator<String> availableLotItr = availableLot.keySet().iterator();
        while (availableLotItr.hasNext()) {
            String availableKey = availableLotItr.next();
            if (vehicleKey.substring(0, 2).equals(availableKey) && method == parkMethod) {
                value = (availableLot.get(availableKey) - 1);
                availableLot.replace(availableKey, value);
                break;
            } else if (vehicleKey.substring(0, 2).equals(availableKey) && method == unParkMethod) {
                value = (availableLot.get(availableKey) + 1);
                availableLot.replace(availableKey, value);
                break;
            }
        }
    }

    public void UnPark(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot) {
        Iterator<String> parkingLotItr = parkingLot.keySet().iterator();
        while (parkingLotItr.hasNext()) {
            String key = parkingLotItr.next();
            if (parkingLot.get(key) == vehicle)
                vehicleKey = key;
        }
        parkingLot.replace(vehicleKey, null);
        updateAvailableLot(availableLot, vehicleKey, unParkMethod);
    }
}
