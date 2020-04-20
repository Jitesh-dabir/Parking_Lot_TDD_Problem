import java.util.*;

public class Attendant {
    String vehicleKey;

    public void park(HashMap<String, Vehicle> parkingLot, Vehicle vehicle, LinkedHashMap<String, Integer> availableLot) {
        String keyAvailable = Collections.max(availableLot.entrySet(), Map.Entry.comparingByValue()).getKey();
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (key.substring(0, 2).equals(keyAvailable) && parkingLot.get(key) == null) {
                parkingLot.replace(key, vehicle);
                int value = (availableLot.get(keyAvailable) - 1);
                availableLot.replace(keyAvailable, value);
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
        Iterator<String> availableLotItr = availableLot.keySet().iterator();
        while (availableLotItr.hasNext()) {
            String key = availableLotItr.next();
            int value = (availableLot.get(key) + 1);
            if (vehicleKey.substring(0, 2).equals(key)) {
                availableLot.replace(key, value);
            }
        }
    }
}
