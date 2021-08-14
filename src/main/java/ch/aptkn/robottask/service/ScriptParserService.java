package ch.aptkn.robottask.service;

import ch.aptkn.robottask.command.*;
import ch.aptkn.robottask.exception.ScriptParserException;
import ch.aptkn.robottask.model.CardinalDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ScriptParserService {
    public List<IScriptCommand> parseScript(String script) throws ScriptParserException {
        ArrayList<IScriptCommand> commands = new ArrayList<>();
        int lineNum = 1;
        for (String line : script.lines().toList()) {
            IScriptCommand command = parseScriptLine(lineNum++, line);
            if (command != null) {
                commands.add(command);
            }
        }
        return commands;
    }

    private IScriptCommand parseScriptLine(long lineNum, String line) throws ScriptParserException {
        String cleanedLine = removeCommentFromLine(line.trim()); // we don't need trailing whitespaces and comments
        String[] parts = StringUtils.split(cleanedLine);
        if (parts.length == 0) {
            // an empty line or end of script
            return null;
        }

        String cmd = parts[0];

        switch (cmd.toUpperCase(Locale.ROOT)) {
            case "WAIT" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 0);
                return new ScriptWaitCommand();
            }
            case "POSITION" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 3);
                int initialPosX = validateNumberArgument(lineNum, line, cmd, parts[1]);
                int initialPosY = validateNumberArgument(lineNum, line, cmd, parts[2]);
                CardinalDirection initialCardinalDirection = validateDirectionArgument(lineNum, line, cmd, parts[3]);
                return new ScriptPositionCommand(initialPosX, initialPosY, initialCardinalDirection);
            }
            case "FORWARD" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 1);
                int steps = validateNumberArgument(lineNum, line, cmd, parts[1]);
                return new ScriptForwardCommand(steps);
            }
            case "TURNAROUND" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 0);
                return new ScriptTurnaroundCommand();
            }
            case "RIGHT" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 0);
                return new ScriptRightCommand();
            }
            case "LEFT" -> {
                checkNumberOfArguments(lineNum, line, cmd, parts, 0);
                return new ScriptLeftCommand();
            }
            default -> throw new ScriptParserException("Unknown command '%s'".formatted(cmd), lineNum, line);
        }
    }

    private void checkNumberOfArguments(long lineNum, String line, String cmd, String[] parts, int required) throws ScriptParserException {
        if (parts.length - 1 != required) {
            throw new ScriptParserException("Invalid number of arguments for '%s' command".formatted(cmd), lineNum, line);
        }
    }

    private String removeCommentFromLine(String line) {
        int ndx = line.indexOf(" //");
        if (ndx != -1) {
            return line.substring(0, ndx);
        }
        return line;
    }

    private int validateNumberArgument(long lineNum, String line, String cmd, String arg) throws ScriptParserException {
        try {
            int n = Integer.parseInt(arg);
            if (n < 0) {
                throw new ScriptParserException("Expected positive integer argument for '%s' command, got '%s'".formatted(cmd, arg), lineNum, line);
            }
            return n;
        } catch (NumberFormatException e) {
            throw new ScriptParserException("Expected integer argument for '%s' command, got '%s'".formatted(cmd, arg), lineNum, line);
        }
    }

    private CardinalDirection validateDirectionArgument(long lineNum, String line, String cmd, String arg) throws ScriptParserException {
        try {
            return CardinalDirection.valueOf(arg.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ScriptParserException("Expected one of (NORTH, EAST, SOUTH, WEST) as direction argument for '%s' command, got '%s'".formatted(cmd, arg), lineNum, line);
        }
    }
}
