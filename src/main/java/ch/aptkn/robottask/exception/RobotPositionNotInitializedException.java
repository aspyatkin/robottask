package ch.aptkn.robottask.exception;

public class RobotPositionNotInitializedException extends Exception {
    public RobotPositionNotInitializedException() {
        super("Robot's position must be initialized first");
    }
}
