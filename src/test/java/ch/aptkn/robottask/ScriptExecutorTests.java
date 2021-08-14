package ch.aptkn.robottask;

import ch.aptkn.robottask.command.*;
import ch.aptkn.robottask.exception.PositionOutOfGridBoundsException;
import ch.aptkn.robottask.exception.RobotPositionAlreadyInitializedException;
import ch.aptkn.robottask.exception.RobotPositionNotInitializedException;
import ch.aptkn.robottask.exception.ScriptCommandExecutionException;
import ch.aptkn.robottask.model.CardinalDirection;
import ch.aptkn.robottask.model.Grid;
import ch.aptkn.robottask.model.Robot;
import ch.aptkn.robottask.service.ScriptExecutorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ScriptExecutorTests {
    private final ScriptExecutorService scriptExecutorService;

    public ScriptExecutorTests() {
        scriptExecutorService = new ScriptExecutorService();
    }

    @Test
    public void testScriptExecutorEmpty() {
        assertThatThrownBy(() -> scriptExecutorService.executeScript(new Grid(5, 5), Collections.emptyList()))
                .isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);
    }

    @Test
    public void testScriptExecutorRobotNotInitialized() {
        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptWaitCommand());
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);

        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptTurnaroundCommand());
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);

        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptLeftCommand());
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);

        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptRightCommand());
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);

        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptForwardCommand(7));
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionNotInitializedException.class);
    }

    @Test
    public void testScriptExecutorRobotAlreadyInitialized() {
        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptPositionCommand(0, 0, CardinalDirection.EAST));
                        add(new ScriptPositionCommand(4, 4, CardinalDirection.WEST));
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(RobotPositionAlreadyInitializedException.class);
    }

    @Test
    public void testScriptExecutorPositionOutOfGridBounds() {
        assertThatThrownBy(() -> {
            scriptExecutorService.executeScript(
                    new Grid(5, 5),
                    new ArrayList<>() {{
                        add(new ScriptPositionCommand(1, 1, CardinalDirection.SOUTH));
                        add(new ScriptForwardCommand(5));
                    }}
            );
        }).isInstanceOf(ScriptCommandExecutionException.class)
                .hasCauseInstanceOf(PositionOutOfGridBoundsException.class);
    }

    @Test
    public void testScriptExecutorBasic() throws ScriptCommandExecutionException {
        Robot robot = scriptExecutorService.executeScript(
                new Grid(5, 5),
                new ArrayList<>() {{
                    add(new ScriptPositionCommand(0, 0, CardinalDirection.EAST));
                    add(new ScriptForwardCommand(4));
                    add(new ScriptRightCommand());
                    add(new ScriptForwardCommand(4));
                    add(new ScriptRightCommand());
                    add(new ScriptForwardCommand(4));
                    add(new ScriptRightCommand());
                    add(new ScriptForwardCommand(3));
                    add(new ScriptRightCommand());
                    add(new ScriptForwardCommand(3));
                    add(new ScriptTurnaroundCommand());
                    add(new ScriptForwardCommand(1));
                    add(new ScriptLeftCommand());
                    add(new ScriptWaitCommand());
                    add(new ScriptForwardCommand(1));
                    add(new ScriptTurnaroundCommand());
                }}
        );
        assertThat(robot.getPosX()).isEqualTo(2);
        assertThat(robot.getPosY()).isEqualTo(2);
        assertThat(robot.getCardinalDirection()).isEqualTo(CardinalDirection.NORTH);
    }
}
