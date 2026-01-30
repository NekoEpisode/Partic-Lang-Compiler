package lang.partic.compiler.visitor.classvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.utils.ClassNameUtils;

public class ImportDeclarationVisitor extends ParticBaseVisitor<Void> {
    private final VisitContext context;

    public ImportDeclarationVisitor(VisitContext context) {
        this.context = context;
    }

    @Override
    public Void visitImportDeclaration(ParticParser.ImportDeclarationContext ctx) {
        ImportManager importManager = context.getImportManager();
        
        // 获取完整的类名 (qualifiedName)
        String fullName = ctx.qualifiedName().getText();
        
        // 获取简单类名 (最后一个部分)
        String simpleName = ClassNameUtils.isClassFullName(fullName) 
            ? fullName.substring(fullName.lastIndexOf('.') + 1)
            : fullName;
        
        // 检查是否有别名 (as IDENTIFIER)
        if (ctx.IDENTIFIER() != null) {
            String alias = ctx.IDENTIFIER().getText();
            importManager.addImport(simpleName, alias, fullName);
        } else {
            importManager.addImport(simpleName, fullName);
        }
        
        return null;
    }
}
