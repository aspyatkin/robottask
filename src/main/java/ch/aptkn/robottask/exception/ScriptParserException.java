package ch.aptkn.robottask.exception;

public class ScriptParserException extends Exception {

    private final long lineNum;
    private final String line;

    public ScriptParserException(String errorMessage, long lineNum, String line) {
        super(errorMessage);
        this.lineNum = lineNum;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Error:\n%s\nLine %d: %s".formatted(getMessage(), lineNum, line);
    }
}
