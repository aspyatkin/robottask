package ch.aptkn.robottask.command;

public class ScriptLeftCommand implements IScriptCommand {
    public ScriptLeftCommand() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        return o instanceof ScriptLeftCommand;
    }
}
