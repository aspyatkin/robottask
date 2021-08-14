package ch.aptkn.robottask.command;

public class ScriptTurnaroundCommand implements IScriptCommand {
    public ScriptTurnaroundCommand() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        return o instanceof ScriptTurnaroundCommand;
    }
}
