package ch.aptkn.robottask.command;

import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.Robot;

public interface IScriptCommand {
    void execute(Robot robot) throws ScriptCommandExecutionException;
}
