package co.edu.unimagdalena.busesreservationandparcels.exceptions;

public class CannotCompleteRequestException extends RuntimeException {
    public CannotCompleteRequestException(String message) {
        super(message);
    }
}
