import java.util.LinkedHashMap;
import java.util.Map;

public interface IAvailability {
    void isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException;

    void isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException;
}
