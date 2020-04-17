import java.util.HashMap;

public class ParkingLotSystem {

    Owner owner = new Owner();
    HashMap<String, Object> parkingLot = new HashMap();
    boolean isFull, isEmpty;


    public ParkingLotSystem() {
    }

    public void park(Vehicle vehicle) throws ParkingLotException {
        if (parkingLot.size() == 2) {
            owner.setParkingLotStatus("Full");
            isFull = true;
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        } else {
            parkingLot.put(vehicle.getVehicleId(), vehicle);
        }
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, "No such a vehicle");
        else if (parkingLot.containsKey(vehicle.getVehicleId()))
            parkingLot.remove(vehicle.getVehicleId());
        else if (parkingLot.size() < 2)
            isEmpty = true;
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