package lang.partic.compiler.visitor;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.ir.*;
import lang.partic.compiler.symbol.ClassSymbol;
import lang.partic.compiler.visitor.classvisitors.ConstructorDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.FieldDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.MethodDeclarationVisitor;

import java.util.List;

/**
 * Pass 2: 类声明访问器
 * 
 * 处理类体，生成 IR（此时符号表已由 Pass 1 建立）
 */
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

    @Override
    public ParticClass visitClassDeclaration(ParticParser.ClassDeclarationContext ctx) {
        String className = ctx.IDENTIFIER().getText();
        
        // 从符号表获取 ClassSymbol 并设置为当前类
        ClassSymbol classSymbol = context.getSymbolTable().resolveClass(className);
        context.setCurrentClass(classSymbol);
        
        // 创建 IR 类对象
        ParticClass particClass = new ParticClass(className);

        // 修饰符
        ParticModifiers modifiers = new ParticModifiers();
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                String mod = modCtx.getText();
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
                String resolvedType = context.resolveType(typeCtx.getText());
                particClass.addImplements(resolvedType);
            }
        }

        // 注解
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = context.resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            particClass.addAnnotation(annotation);
        }

        // 父类
        if (ctx.type() != null) {
            String extendsClass = context.resolveType(ctx.type().getText());
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
            }
        }

        // 清除当前类
        context.setCurrentClass(null);
        
        return particClass;
    }
}
