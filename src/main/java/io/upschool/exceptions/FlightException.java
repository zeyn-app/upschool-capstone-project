package io.upschool.exceptions;

public final class FlightException extends RuntimeException{
    public static String DATA_NOT_FOUND = "Flight not found";
    public FlightException(String message){
        super(message);
    }
}
