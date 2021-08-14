package ch.aptkn.robottask.command;

public class ScriptRightCommand implements IScriptCommand {
    public ScriptRightCommand() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        return o instanceof ScriptRightCommand;
    }
}
