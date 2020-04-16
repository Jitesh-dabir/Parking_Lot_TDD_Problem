public class ParkingLotException extends Exception {

    enum MyException {
        PARKING_LOT_IS_FULL, NO_SUCH_A_VEHICLE
    }

    MyException type;

    public ParkingLotException(MyException type, String message) {
        this.type = type;
    }
}
