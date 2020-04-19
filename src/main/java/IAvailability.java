import java.util.LinkedHashMap;
import java.util.Map;

public interface IAvailability {
    String isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException;

    String isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException;
}
