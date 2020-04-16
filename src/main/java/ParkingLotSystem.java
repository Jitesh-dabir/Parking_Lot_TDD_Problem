public class ParkingLotSystem {

    private Object vehicle = null;

    public ParkingLotSystem() {
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException(ParkingLotException.MyException.PARKING_LOT_IS_FULL, "Parking lot is full");
        this.vehicle = vehicle;
    }

    public void unPark(Object vehicle){
        if (this.vehicle.equals(vehicle))
            this.vehicle = null;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }

    public boolean isVehicleUnParked(Object vehicle) {
        if (this.vehicle != vehicle)
            return true;
        return false;
    }
}