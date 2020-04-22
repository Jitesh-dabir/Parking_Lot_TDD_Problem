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
    public void isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException {
        if (!parkingLot.containsValue(null)) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
    }

    public void isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null) {
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        }
    }
}