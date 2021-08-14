package ch.aptkn.robottask.exception;

public class ScriptCommandExecutionException extends Exception {
    public ScriptCommandExecutionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Execution error: %s".formatted(getCause().getMessage());
    }
}
