package ch.aptkn.robottask.service;

import ch.aptkn.robottask.command.IScriptCommand;
import ch.aptkn.robottask.exception.RobotPositionNotInitializedException;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.Grid;
import ch.aptkn.robottask.model.Robot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptExecutorService {
    public Robot executeScript(Grid grid, List<IScriptCommand> commands) throws ScriptCommandExecutionException {
        if (commands.isEmpty()) {
            throw new ScriptCommandExecutionException(new RobotPositionNotInitializedException());
        }
        Robot robot = new Robot(grid);
        for (IScriptCommand command : commands) {
            command.execute(robot);
        }
        return robot;
    }
}
