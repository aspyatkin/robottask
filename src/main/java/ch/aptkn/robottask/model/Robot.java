package ch.aptkn.robottask.model;

import ch.aptkn.robottask.exception.PositionOutOfGridBoundsException;
import ch.aptkn.robottask.exception.RobotPositionAlreadyInitializedException;
import ch.aptkn.robottask.exception.RobotPositionNotInitializedException;

public class Robot {
    private final Grid grid;
    private int posX = -1;
    private int posY = -1;
    private CardinalDirection cardinalDirection;

    public Robot(Grid grid) {
        this.grid = grid;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public CardinalDirection getCardinalDirection() {
        return cardinalDirection;
    }

    private boolean isInitialized() {
        return posX != -1 && posY != -1 && cardinalDirection != null;
    }

    private void checkInitialized() throws RobotPositionNotInitializedException {
        if (!isInitialized()) {
            throw new RobotPositionNotInitializedException();
        }
    }

    private void checkNotInitialized() throws RobotPositionAlreadyInitializedException {
        if (isInitialized()) {
            throw new RobotPositionAlreadyInitializedException();
        }
    }

    public void init(int posX, int posY, CardinalDirection cardinalDirection) throws PositionOutOfGridBoundsException, RobotPositionAlreadyInitializedException {
        checkNotInitialized();
        grid.checkBounds(posX, posY);
        this.posX = posX;
        this.posY = posY;
        this.cardinalDirection = cardinalDirection;
    }

    public void turnAround() throws RobotPositionNotInitializedException {
        checkInitialized();
        cardinalDirection = switch (cardinalDirection) {
            case NORTH -> CardinalDirection.SOUTH;
            case EAST -> CardinalDirection.WEST;
            case SOUTH -> CardinalDirection.NORTH;
            case WEST -> CardinalDirection.EAST;
        };
    }

    public void turnRight() throws RobotPositionNotInitializedException {
        checkInitialized();
        cardinalDirection = switch (cardinalDirection) {
            case NORTH -> CardinalDirection.EAST;
            case EAST -> CardinalDirection.SOUTH;
            case SOUTH -> CardinalDirection.WEST;
            case WEST -> CardinalDirection.NORTH;
        };
    }

    public void turnLeft() throws RobotPositionNotInitializedException {
        checkInitialized();
        cardinalDirection = switch (cardinalDirection) {
            case NORTH -> CardinalDirection.WEST;
            case EAST -> CardinalDirection.NORTH;
            case SOUTH -> CardinalDirection.EAST;
            case WEST -> CardinalDirection.SOUTH;
        };
    }

    public void doWait() throws RobotPositionNotInitializedException {
        checkInitialized();
    }

    public void forward(int steps) throws RobotPositionNotInitializedException, PositionOutOfGridBoundsException {
        checkInitialized();
        int newPosX = posX;
        int newPosY = posY;
        switch (cardinalDirection) {
            case NORTH -> newPosY -= steps;
            case EAST -> newPosX += steps;
            case SOUTH -> newPosY += steps;
            case WEST -> newPosX -= steps;
        }
        grid.checkBounds(newPosX, newPosY);
        posX = newPosX;
        posY = newPosY;
    }
}
