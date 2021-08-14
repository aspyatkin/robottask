package ch.aptkn.robottask.command;

import ch.aptkn.robottask.exception.PositionOutOfGridBoundsException;
import ch.aptkn.robottask.exception.RobotPositionAlreadyInitializedException;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.CardinalDirection;
import ch.aptkn.robottask.model.Robot;

public class ScriptPositionCommand implements IScriptCommand {
    private final int initialPosX;
    private final int initialPosY;
    private final CardinalDirection initialCardinalDirection;

    public ScriptPositionCommand(int initialPosX, int initialPosY, CardinalDirection initialCardinalDirection) {
        this.initialPosX = initialPosX;
        this.initialPosY = initialPosY;
        this.initialCardinalDirection = initialCardinalDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ScriptPositionCommand other))
            return false;
        return other.initialPosX == initialPosX && other.initialPosY == initialPosY && other.initialCardinalDirection.equals(initialCardinalDirection);
    }

    @Override
    public void execute(Robot robot) throws ScriptCommandExecutionException {
        try {
            robot.init(initialPosX, initialPosY, initialCardinalDirection);
        } catch (PositionOutOfGridBoundsException | RobotPositionAlreadyInitializedException e) {
            throw new ScriptCommandExecutionException(e);
        }
    }
}
