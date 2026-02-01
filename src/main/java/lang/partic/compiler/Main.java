package lang.partic.compiler;

import lang.partic.compiler.antlr.ParticLexer;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.compile.CompileResult;
import lang.partic.compiler.compile.ParticCompiler;
import lang.partic.compiler.ir.ParticProgram;
import lang.partic.compiler.symbol.ExternalClassLoader;
import lang.partic.compiler.visitor.FrontendVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    void main(String[] args) {
        // 解析命令行参数
        String sourceFile = null;
        String outputDir = "./debug";
        String classpath = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-cp") || args[i].equals("-classpath")) {
                if (i + 1 < args.length) {
                    classpath = args[++i];
                }
            } else if (args[i].equals("-d")) {
                if (i + 1 < args.length) {
                    outputDir = args[++i];
                }
            } else if (!args[i].startsWith("-")) {
                sourceFile = args[i];
            }
        }

        if (sourceFile == null) {
            System.err.println("用法: java Main [选项] <源文件>");
            System.err.println("选项:");
            System.err.println("  -cp, -classpath <路径>  设置类路径（用 ; 分隔多个路径）");
            System.err.println("  -d <目录>               输出目录（默认: ./debug）");
            System.exit(1);
        }

        // 设置自定义 ClassLoader
        if (classpath != null) {
            setupCustomClassLoader(classpath);
        }

        // 读取源文件
        String source;
        try {
            source = Files.readString(Path.of(sourceFile));
        } catch (IOException e) {
            log.error("读取源文件失败: {}", sourceFile, e);
            System.exit(1);
            return;
        }

        log.debug("源码: {}", source);

        CharStream charStream = CharStreams.fromString(source);
        ParticLexer lexer = new ParticLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParticParser parser = new ParticParser(tokens);

        ParticParser.ProgramContext tree = parser.program();

        FrontendVisitor visitor = new FrontendVisitor(Path.of(outputDir));
        ParticProgram program = visitor.visitProgram(tree);

        ParticCompiler compiler = new ParticCompiler(program);
        CompileResult compileResult = compiler.compile();

        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    file.delete();
                }
            }
        }

        for (Map.Entry<String, byte[]> entry : compileResult.bytecodes().entrySet()) {
            byte[] bytecode = entry.getValue();
            System.out.println("得到的class字节码: " + Arrays.toString(bytecode));
            File file = new File(outputDir + "/" + entry.getKey() + ".class");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                Files.write(file.toPath(), bytecode);
                System.out.println("编译成功: " + file.getAbsolutePath());
            } catch (IOException e) {
                log.error("保存文件时出现错误", e);
            }
        }
    }

    /**
     * 设置自定义 ClassLoader，支持自定义 classpath
     */
    private void setupCustomClassLoader(String classpath) {
        try {
            String[] paths = classpath.split(";");
            List<URL> urls = new ArrayList<>();

            for (String path : paths) {
                File file = new File(path);
                if (file.exists()) {
                    urls.add(file.toURI().toURL());
                    log.debug("添加到 classpath: {}", file.getAbsolutePath());
                } else {
                    log.warn("classpath 路径不存在: {}", path);
                }
            }

            if (!urls.isEmpty()) {
                URLClassLoader classLoader = new URLClassLoader(
                    urls.toArray(new URL[0]),
                    Thread.currentThread().getContextClassLoader()
                );
                Thread.currentThread().setContextClassLoader(classLoader);
                log.debug("设置自定义 ClassLoader 成功");
            }
        } catch (Exception e) {
            log.error("设置 ClassLoader 失败", e);
        }
    }
}