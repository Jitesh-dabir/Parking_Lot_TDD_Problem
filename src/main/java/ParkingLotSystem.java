public class ParkingLotSystem {

    private Object vehicle = null;

    public ParkingLotSystem() {
    }

    public boolean park(Object vehicle) {
        if (this.vehicle != null)
            return false;
        this.vehicle = vehicle;
        return true;
    }
}