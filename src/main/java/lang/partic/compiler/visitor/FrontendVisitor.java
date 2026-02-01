package lang.partic.compiler.visitor;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticProgram;
import lang.partic.compiler.symbol.SymbolTable;
import lang.partic.compiler.visitor.classvisitors.ImportDeclarationVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * 前端访问器 - 编译的第一阶段
 * 
 * 采用两遍扫描策略：
 * 1. Pass 1 (DeclarationCollector): 收集所有声明，建立符号表
 * 2. Pass 2 (ClassDeclarationVisitor): 处理方法体，生成 IR
 */
public class FrontendVisitor extends ParticBaseVisitor<ParticProgram> {
    private static final Logger log = LoggerFactory.getLogger(FrontendVisitor.class);
    
    private final Path sourceFile;
    private final VisitContext context;
    private final ImportDeclarationVisitor importVisitor;
    private final DeclarationCollector declarationCollector;
    private final ClassDeclarationVisitor classDeclarationVisitor;

    public FrontendVisitor(Path sourceFile) {
        this.sourceFile = sourceFile;
        this.context = new VisitContext();
        this.importVisitor = new ImportDeclarationVisitor(context);
        this.declarationCollector = new DeclarationCollector(
            context.getSymbolTable(), 
            context.getImportManager()
        );
        this.classDeclarationVisitor = new ClassDeclarationVisitor(context);
    }

    @Override
    public ParticProgram visitProgram(ParticParser.ProgramContext ctx) {
        ParticProgram program = new ParticProgram(sourceFile.getFileName().toString());

        // ========== 阶段 0: 处理 import 声明 ==========
        log.debug("=== Pass 0: 处理 import 声明 ===");
        for (ParticParser.ImportDeclarationContext importCtx : ctx.importDeclaration()) {
            importVisitor.visitImportDeclaration(importCtx);
        }

        // ========== 阶段 1: 收集声明，建立符号表 ==========
        log.debug("=== Pass 1: 收集声明，建立符号表 ===");
        declarationCollector.visitProgram(ctx);
        
        // 调试：打印符号表
        if (log.isDebugEnabled()) {
            context.getSymbolTable().dump();
        }

        // ========== 阶段 2: 处理方法体，生成 IR ==========
        log.debug("=== Pass 2: 处理方法体，生成 IR ===");
        for (ParticParser.TypeDeclarationContext typeCtx : ctx.typeDeclaration()) {
            if (typeCtx.classDeclaration() != null) {
                ParticClass particClass = classDeclarationVisitor.visitClassDeclaration(typeCtx.classDeclaration());
                if (particClass != null) {
                    program.addClass(particClass);
                }
            }
            // TODO: 处理 interface 和 enum
        }

        return program;
    }

    public Path getSourceFile() {
        return sourceFile;
    }

    public VisitContext getContext() {
        return context;
    }

    public SymbolTable getSymbolTable() {
        return context.getSymbolTable();
    }
}
