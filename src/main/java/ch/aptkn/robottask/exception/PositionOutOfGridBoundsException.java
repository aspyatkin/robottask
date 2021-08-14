package ch.aptkn.robottask.exception;

public class PositionOutOfGridBoundsException extends Exception {
    public PositionOutOfGridBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
