package co.edu.unimagdalena.busesreservationandparcels.exceptions;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException(String message) {
        super(message);
    }
}
