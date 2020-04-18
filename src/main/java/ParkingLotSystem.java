import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ParkingLotSystem {

    private String isFull;
    private int parkingLotCapacity = 2;
    LinkedHashMap<String, Vehicle> parkingLot = new LinkedHashMap();
    private List<IObservable> observableList = new ArrayList<>();
    Owner owner = null;
    Attendant attendant = null;

    public ParkingLotSystem() {
    }

    public ParkingLotSystem(Owner owner, Attendant attendant) {
        this.owner = owner;
        this.attendant = attendant;
    }

    public void addObserver(IObservable iObservable) {
        this.observableList.add(iObservable);
    }

    public void setStatus(String isFull) {
        this.isFull = isFull;
        for (IObservable observable : this.observableList) {
            observable.update(this.isFull);
        }
    }

    public void park(Vehicle vehicle) throws ParkingLotException {
        boolean isAvailable = owner.isAvailable(parkingLot, parkingLotCapacity);
        if (isAvailable) {
            attendant.park(parkingLot, vehicle);
        }
        if (parkingLot.size() == parkingLotCapacity)
            setStatus("Full");
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        boolean isPresent = owner.isPresent(parkingLot, vehicle);
        if (isPresent) {
            attendant.UnPark(parkingLot, vehicle);
        }
        if (parkingLot.size() < parkingLotCapacity)
            setStatus("Have Space");
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