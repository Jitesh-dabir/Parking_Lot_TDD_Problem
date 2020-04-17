import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotSystem {

    private String isFull;
    private int parkingLotCapacity = 2;
    HashMap<String, Object> parkingLot = new HashMap();
    private List<IObservable> observableList = new ArrayList<>();

    public ParkingLotSystem() {
    }

    public void addObserver(IObservable iObservable) {
        this.observableList.add(iObservable);
    }

    public void setInformation(String isFull) {
        this.isFull = isFull;
        for (IObservable observable : this.observableList) {
            observable.update(this.isFull);
        }
    }

    public void park(Vehicle vehicle) throws ParkingLotException {
        if (parkingLot.size() < parkingLotCapacity) {
            parkingLot.put(vehicle.getVehicleId(), vehicle);
        } else if (parkingLot.size() == parkingLotCapacity) {
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        if (parkingLot.size() == parkingLotCapacity)
            setInformation("Full");
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        else if (parkingLot.containsKey(vehicle.getVehicleId()))
            parkingLot.remove(vehicle.getVehicleId());
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        if (parkingLot.containsKey(vehicle.getVehicleId()))
            return true;
        return false;
    }

    public boolean isVehicleUnParked(Vehicle vehicle) {
        if (!parkingLot.containsKey(vehicle.getVehicleId()))
            return true;
        return false;
    }
}