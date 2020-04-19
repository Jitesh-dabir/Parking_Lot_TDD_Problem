import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Owner implements IObservable, IAvailability {
    private String parkingLotStatus;

    @Override
    public void update(Object status) {
        this.setParkingLotStatus((String) status);
    }

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    public void setParkingLotStatus(String isFull) {
        this.parkingLotStatus = isFull;
    }

    @Override
    public String isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException {
        if (!parkingLot.containsValue(null)) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (parkingLot.get(key) == null)
                return key;
        }
        return null;
    }

    public String isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null) {
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        }
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (parkingLot.get(key) == vehicle)
                return key;
        }
        return null;
    }
}