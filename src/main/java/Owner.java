import java.util.LinkedHashMap;
import java.util.Map;

public class Owner implements IObservable, IAvailability {
    private String parkingLotStatus;

    @Override
    public void update(Object status) {
        this.setParkingLotStatus((String) status);
    }

    @Override
    public boolean isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        else if (parkingLot.containsKey(vehicle.getVehicleId()))
            return true;
        return false;
    }

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    public void setParkingLotStatus(String isFull) {
        this.parkingLotStatus = isFull;
    }

    @Override
    public boolean isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException {
        if (parkingLot.size() < parkingLotCapacity) {
            return true;
        } else if (parkingLot.size() == parkingLotCapacity) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        return false;
    }
}