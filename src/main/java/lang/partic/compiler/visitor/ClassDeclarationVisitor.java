package lang.partic.compiler.visitor;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.ir.*;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.visitor.classvisitors.ConstructorDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.FieldDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.MethodDeclarationVisitor;

import java.util.List;

public class ClassDeclarationVisitor extends ParticBaseVisitor<ParticClass> {
    private final VisitContext context;
    private final FieldDeclarationVisitor fieldVisitor;
    private final MethodDeclarationVisitor methodVisitor;
    private final ConstructorDeclarationVisitor constructorVisitor;

    public ClassDeclarationVisitor(VisitContext context) {
        this.context = context;
        this.fieldVisitor = new FieldDeclarationVisitor(context);
        this.methodVisitor = new MethodDeclarationVisitor(context);
        this.constructorVisitor = new ConstructorDeclarationVisitor(context);
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
    public ParticClass visitClassDeclaration(ParticParser.ClassDeclarationContext ctx) {
        // 创建类对象
        ParticClass particClass = new ParticClass(ctx.IDENTIFIER().getText());

        // 修饰符
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
        particClass.setModifiers(modifiers);

        // 实现的接口
        if (ctx.typeList() != null) {
            for (ParticParser.TypeContext typeCtx : ctx.typeList().type()) {
                String interfaceType = typeCtx.getText();
                String resolvedType = resolveType(interfaceType);
                particClass.addImplements(resolvedType);
            }
        }

        // 注解
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            // 处理注解参数
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            particClass.addAnnotation(annotation);
        }

        // 父类
        if (ctx.type() != null) {
            String parentType = ctx.type().getText();
            String extendsClass = resolveType(parentType);
            particClass.setExtendsClass(extendsClass);
        }

        // 处理类体
        if (ctx.classBody() != null) {
            for (ParticParser.ClassBodyDeclarationContext bodyCtx : ctx.classBody().classBodyDeclaration()) {
                if (bodyCtx.fieldDeclaration() != null) {
                    List<ParticField> declaredFields = fieldVisitor.visitFieldDeclaration(bodyCtx.fieldDeclaration());
                    for (ParticField field : declaredFields) {
                        particClass.addField(field);
                    }
                } else if (bodyCtx.methodDeclaration() != null) {
                    ParticMethod method = methodVisitor.visitMethodDeclaration(bodyCtx.methodDeclaration());
                    particClass.addMethod(method);
                } else if (bodyCtx.constructorDeclaration() != null) {
                    ParticConstructor constructor = constructorVisitor.visitConstructorDeclaration(bodyCtx.constructorDeclaration());
                    particClass.addConstructor(constructor);
                }
                // staticBlock 会被自动忽略（返回 null）
            }
        }

        return particClass;
    }
}
