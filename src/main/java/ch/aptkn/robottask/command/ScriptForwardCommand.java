package ch.aptkn.robottask.command;

import ch.aptkn.robottask.exception.PositionOutOfGridBoundsException;
import ch.aptkn.robottask.exception.RobotPositionNotInitializedException;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.Robot;

public class ScriptForwardCommand implements IScriptCommand {
    public int steps;

    public ScriptForwardCommand(int steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ScriptForwardCommand other))
            return false;
        return other.steps == steps;
    }

    @Override
    public void execute(Robot robot) throws ScriptCommandExecutionException {
        try {
            robot.forward(steps);
        } catch (RobotPositionNotInitializedException | PositionOutOfGridBoundsException e) {
            throw new ScriptCommandExecutionException(e);
        }
    }
}
