package ch.aptkn.robottask.command;

public class ScriptWaitCommand implements IScriptCommand {
    public ScriptWaitCommand() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        return o instanceof ScriptWaitCommand;
    }
}
