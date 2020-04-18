import java.util.HashMap;
import java.util.LinkedHashMap;

public class Attendant {

    public void park(HashMap<String, Vehicle> parkingLot, Vehicle vehicle) {
        parkingLot.put(vehicle.getVehicleId(), vehicle);
    }

    public void UnPark(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) {
        parkingLot.remove(vehicle.getVehicleId());
    }
}
