package lang.partic.compiler.visitor;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.ir.ParticClass;
import lang.partic.compiler.ir.ParticProgram;
import lang.partic.compiler.visitor.classvisitors.ImportDeclarationVisitor;

import java.nio.file.Path;

public class FrontendVisitor extends ParticBaseVisitor<ParticProgram> {
    private final Path sourceFile;
    private final VisitContext context;
    private final ClassDeclarationVisitor classDeclarationVisitor;
    private final ImportDeclarationVisitor importVisitor;

    public FrontendVisitor(Path sourceFile) {
        this.sourceFile = sourceFile;
        this.context = new VisitContext();
        this.classDeclarationVisitor = new ClassDeclarationVisitor(context);
        this.importVisitor = new ImportDeclarationVisitor(context);
    }

    @Override
    public ParticProgram visitProgram(ParticParser.ProgramContext ctx) {
        ParticProgram program = new ParticProgram(sourceFile.getFileName().toString());

        // 处理 import 声明 - 这里需要手动控制顺序（先处理 import）
        for (ParticParser.ImportDeclarationContext importCtx : ctx.importDeclaration()) {
            importVisitor.visitImportDeclaration(importCtx);
        }

        // 收集所有类型声明
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
}
