package lang.partic.compiler.compile;

import java.util.Map;

/**
 * @param bytecodes className to bytecode
 */
public record CompileResult(Map<String, byte[]> bytecodes) {
}
