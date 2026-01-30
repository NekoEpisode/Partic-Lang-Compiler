package lang.partic.compiler.visitor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lang.partic.compiler.StaticValues;
import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.visitor.classvisitors.ImportDeclarationVisitor;

import java.nio.file.Path;
import java.time.Instant;

public class FrontendVisitor extends ParticBaseVisitor<JsonObject> {
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
    public JsonObject visitProgram(ParticParser.ProgramContext ctx) {
        JsonObject ir = new JsonObject();
        JsonObject metadata = new JsonObject();

        // 构建metadata
        Instant now = Instant.now();
        String timestamp = now.toString();
        metadata.addProperty("generated", timestamp);
        metadata.addProperty("ir_version", StaticValues.IR_VERSION);
        metadata.addProperty("source_file", sourceFile.getFileName().toString());
        ir.add("metadata", metadata);

        // 处理 import 声明
        for (ParticParser.ImportDeclarationContext importCtx : ctx.importDeclaration()) {
            importVisitor.visitImportDeclaration(importCtx);
        }

        // 收集所有类型声明
        JsonArray classes = new JsonArray();
        for (ParticParser.TypeDeclarationContext typeCtx : ctx.typeDeclaration()) {
            if (typeCtx.classDeclaration() != null) {
                ParticParser.ClassDeclarationContext classCtx = typeCtx.classDeclaration();
                JsonObject classIR = visitClassDeclaration(classCtx);
                classes.add(classIR);
            }
            // TODO: 处理 interface 和 enum
        }
        ir.add("classes", classes);

        return ir;
    }

    @Override
    public JsonObject visitClassDeclaration(ParticParser.ClassDeclarationContext ctx) {
        return classDeclarationVisitor.visitClassDeclaration(ctx);
    }

    public Path getSourceFile() {
        return sourceFile;
    }
}
