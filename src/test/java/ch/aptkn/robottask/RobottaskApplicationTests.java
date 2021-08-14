package ch.aptkn.robottask;

import ch.aptkn.robottask.command.*;
import ch.aptkn.robottask.exception.ScriptParserException;
import ch.aptkn.robottask.model.CardinalDirection;
import ch.aptkn.robottask.service.ScriptParserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class RobottaskApplicationTests {
    private final ScriptParserService scriptParserService;

    public RobottaskApplicationTests() {
        this.scriptParserService = new ScriptParserService();
    }

    @Test
    public void testScriptParserEmpty() throws ScriptParserException {
        List<IScriptCommand> r = scriptParserService.parseScript("");
        assertThat(r.size()).isZero();
    }

    @Test
    public void testScriptParserBasic() throws ScriptParserException {
        String script = """
                POSITION 1 3 EAST //sets the initial position for the robot
                FORWARD 3 //lets the robot do 3 steps forward
                WAIT //lets the robot do nothing
                TURNAROUND //lets the robot turn around
                				
                FORWARD 1 //lets the robot do 1 step forward
                RIGHT //lets the robot turn right
                FORWARD 2 //lets the robot do 2 steps forward
                LEFT
                				
                """;
        List<IScriptCommand> r = scriptParserService.parseScript(script);
        assertThat(r.size()).isEqualTo(8);
        assertThat(r.get(0)).isEqualTo(new ScriptPositionCommand(1, 3, CardinalDirection.EAST));
        assertThat(r.get(1)).isEqualTo(new ScriptForwardCommand(3));
        assertThat(r.get(2)).isEqualTo(new ScriptWaitCommand());
        assertThat(r.get(3)).isEqualTo(new ScriptTurnaroundCommand());
        assertThat(r.get(4)).isEqualTo(new ScriptForwardCommand(1));
        assertThat(r.get(5)).isEqualTo(new ScriptRightCommand());
        assertThat(r.get(6)).isEqualTo(new ScriptForwardCommand(2));
        assertThat(r.get(7)).isEqualTo(new ScriptLeftCommand());
    }

    @Test
    public void testScriptParserFailing() {
        assertThatThrownBy(() -> {
            scriptParserService.parseScript("BACKWARD");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Unknown command 'BACKWARD'");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("WAIT 5");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Invalid number of arguments for 'WAIT' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("TURNAROUND 4");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Invalid number of arguments for 'TURNAROUND' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("LEFT 1 2");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Invalid number of arguments for 'LEFT' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("RIGHT 0 //hello");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Invalid number of arguments for 'RIGHT' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("FORWARD");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Invalid number of arguments for 'FORWARD' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("POSITION A 0 NORTH");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Expected integer argument for 'POSITION' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("POSITION 6 -2 WEST");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Expected positive integer argument for 'POSITION' command");

        assertThatThrownBy(() -> {
            scriptParserService.parseScript("POSITION 1 2 ABC");
        }).isInstanceOf(ScriptParserException.class).hasMessageContaining("Expected one of (NORTH, EAST, SOUTH, WEST) as direction argument for 'POSITION' command");
    }
}
