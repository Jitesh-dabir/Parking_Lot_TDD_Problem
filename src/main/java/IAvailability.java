import java.util.LinkedHashMap;
import java.util.Map;

public interface IAvailability {
    public boolean isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException;
    boolean isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException;
}
