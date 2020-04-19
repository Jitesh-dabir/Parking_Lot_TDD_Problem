import java.util.HashMap;
import java.util.LinkedHashMap;

public class Attendant {

    public void park(HashMap<String, Vehicle> parkingLot, Vehicle vehicle, String getKey) {
        parkingLot.replace(getKey, vehicle);
    }

    public void UnPark(LinkedHashMap<String, Vehicle> parkingLot, String getKey) {
        parkingLot.replace(getKey, null);
    }
}
