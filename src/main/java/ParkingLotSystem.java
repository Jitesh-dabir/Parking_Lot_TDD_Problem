import java.util.*;

public class ParkingLotSystem {

    private String isFull;
    private int parkingLotCapacity = 11;
    private int numberOfSlot = 1;
    LinkedHashMap<String, Vehicle> parkingLot = null;
    private List<IObservable> observableList = new ArrayList<>();
    Owner owner = null;
    Attendant attendant = null;

    public ParkingLotSystem(Owner owner, Attendant attendant, LinkedHashMap parkingLot) {
        this.owner = owner;
        this.attendant = attendant;
        this.parkingLot = parkingLot;
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
        String getKey = owner.isAvailable(parkingLot, parkingLotCapacity);
        attendant.park(parkingLot, vehicle, getKey);

        if (!parkingLot.containsValue(null))
            setStatus("Full");
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        String getKey = owner.isPresent(parkingLot, vehicle);
        attendant.UnPark(parkingLot, getKey);
        if (parkingLot.containsValue(null)) {
            setStatus("Have Space");
        }
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        if (parkingLot.containsValue(vehicle))
            return true;
        return false;
    }

    public boolean isVehicleUnParked(Vehicle vehicle) {
        if (!parkingLot.containsValue(vehicle))
            return true;
        return false;
    }

    public int getMyCarParkingNumber(Vehicle vehicle) {
        Set<String> keys = parkingLot.keySet();
        List<String> listKeys = new ArrayList<String>(keys);
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (parkingLot.get(key) == vehicle)
                return listKeys.indexOf(key);
        }
        return 0;
    }

    public void createParkingLot() {
        int counter = 1, index = 0, slot = 1, length = 0, slotCapacity = 0;
        while (index != parkingLotCapacity) {
            slotCapacity = parkingLotCapacity / numberOfSlot;
            if (parkingLotCapacity % numberOfSlot != 0)
                slotCapacity += 1;
            if (counter == slotCapacity + 1) {
                counter = 1;
                slot++;
            }
            String number1 = Integer.toString(counter);
            length = number1.length();
            if (length == 1) {
                number1 = "0" + number1;
            }
            String key = "P" + Integer.toString(slot) + number1;
            parkingLot.put(key, null);
            index++;
            counter++;
        }
    }
}