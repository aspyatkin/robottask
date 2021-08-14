package ch.aptkn.robottask.command;

public class ScriptForwardCommand implements IScriptCommand {
    public int steps;

    public ScriptForwardCommand(int steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ScriptForwardCommand other))
            return false;
        return other.steps == steps;
    }
}
