package lang.partic.compiler.visitor.classvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.*;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.visitor.statementvisitors.StatementVisitor;

public class ConstructorDeclarationVisitor extends ParticBaseVisitor<ParticConstructor> {
    private final VisitContext context;

    public ConstructorDeclarationVisitor(VisitContext context) {
        this.context = context;
    }

    /**
     * 解析类型名称为完整类名
     * @param typeName 类型名称（可能是简单名、别名或完整名）
     * @return 完整类名
     * @throws CompileError 如果无法解析类型
     */
    private String resolveType(String typeName) {
        ImportManager importManager = context.getImportManager();
        String fullName = importManager.findFullName(typeName);
        
        if (fullName == null) {
            throw new CompileError("Cannot resolve type: " + typeName);
        }
        
        return fullName;
    }

    @Override
    public ParticConstructor visitConstructorDeclaration(ParticParser.ConstructorDeclarationContext ctx) {
        ParticConstructor constructor = new ParticConstructor();
        
        // 构造函数修饰符
        ParticModifiers modifiers = new ParticModifiers();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                String mod = modCtx.getText();
                // 访问修饰符：pub, priv, prot, pack
                switch (mod) {
                    case "pub", "priv", "prot", "pack" -> modifiers.setAccess(mod);
                    default -> modifiers.addOther(mod);
                }
            }
        }
        constructor.setModifiers(modifiers);
        
        // 参数
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                // 参数类型 - 解析为完整类名
                String paramType = paramCtx.type().getText();
                String resolvedParamType = resolveType(paramType);
                String paramName = paramCtx.IDENTIFIER().getText();
                
                ParticParameter param = new ParticParameter(resolvedParamType, paramName);
                
                // 参数注解
                for (ParticParser.AnnotationContext annCtx : paramCtx.annotation()) {
                    String annotationType = annCtx.qualifiedName().getText();
                    String resolvedAnnotation = resolveType(annotationType);
                    ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
                    
                    if (annCtx.elementValuePairs() != null) {
                        // TODO: 解析注解参数
                    }
                    
                    param.addAnnotation(annotation);
                }
                
                constructor.addParameter(param);
            }
        }
        
        // 注解
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            constructor.addAnnotation(annotation);
        }
        
        // 构造函数体
        if (ctx.constructorBody() != null) {
            ParticMethodBody body = new ParticMethodBody();
            
            // 使用StatementVisitor处理构造函数体
            StatementVisitor stmtVisitor = new StatementVisitor(body, context);
            for (ParticParser.StatementContext stmtCtx : ctx.constructorBody().statement()) {
                stmtVisitor.visit(stmtCtx);
            }
            
            constructor.setBody(body);
        }
        
        return constructor;
    }
}
