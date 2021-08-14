package ch.aptkn.robottask.exception;

public class RobotPositionAlreadyInitializedException extends Exception {
    public RobotPositionAlreadyInitializedException() {
        super("Robot's position cannot be initialized twice");
    }
}
