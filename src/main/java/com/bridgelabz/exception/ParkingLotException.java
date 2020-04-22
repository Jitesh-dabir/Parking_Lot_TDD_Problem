package com.bridgelabz.exception;

public class ParkingLotException extends Exception {

    public enum MyException {
        PARKING_LOT_IS_FULL, NO_SUCH_A_VEHICLE, PARKING_LOT_IS_FULL_FOR_LARGE_VEHICLE
    }

    public MyException type;

    public ParkingLotException(MyException type, String message) {
        super(message);
        this.type = type;
    }
}
