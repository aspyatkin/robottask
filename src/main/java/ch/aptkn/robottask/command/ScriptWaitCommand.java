package ch.aptkn.robottask.command;

import ch.aptkn.robottask.exception.RobotPositionNotInitializedException;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.Robot;

public class ScriptWaitCommand implements IScriptCommand {
    public ScriptWaitCommand() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        return o instanceof ScriptWaitCommand;
    }

    @Override
    public void execute(Robot robot) throws ScriptCommandExecutionException {
        try {
            robot.doWait();
        } catch (RobotPositionNotInitializedException e) {
            throw new ScriptCommandExecutionException(e);
        }
    }
}
