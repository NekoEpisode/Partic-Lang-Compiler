package lang.partic.compiler.exceptions;

public class CompileError extends RuntimeException {
    public CompileError(String message) {
        super(message);
    }
}
