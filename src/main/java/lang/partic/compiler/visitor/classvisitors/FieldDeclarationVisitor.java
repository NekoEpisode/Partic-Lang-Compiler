package lang.partic.compiler.visitor.classvisitors;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.ir.ParticAnnotation;
import lang.partic.compiler.ir.ParticField;
import lang.partic.compiler.ir.ParticModifiers;
import lang.partic.compiler.manager.ImportManager;

import java.util.ArrayList;
import java.util.List;

public class FieldDeclarationVisitor extends ParticBaseVisitor<List<ParticField>> {
    private final VisitContext context;

    public FieldDeclarationVisitor(VisitContext context) {
        this.context = context;
    }

    /**
     * 解析类型名称为完整类名
     * 如果无法解析，返回原始名称让后端处理
     */
    private String resolveType(String typeName) {
        ImportManager importManager = context.getImportManager();
        String fullName = importManager.findFullName(typeName);
        
        // 找不到就返回原值，让后端处理
        return fullName != null ? fullName : typeName;
    }

    @Override
    public List<ParticField> visitFieldDeclaration(ParticParser.FieldDeclarationContext ctx) {
        List<ParticField> fields = new ArrayList<>();
        
        // 提取公共信息
        // 字段修饰符
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
        
        // 字段类型 - 解析为完整类名
        String fieldType = ctx.type().getText();
        String resolvedType = resolveType(fieldType);
        
        // 注解
        List<ParticAnnotation> annotations = new ArrayList<>();
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = resolveType(annotationType);
            ParticAnnotation annotation = new ParticAnnotation(resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                // TODO: 解析注解参数
            }
            
            annotations.add(annotation);
        }
        
        // 处理每个变量声明
        for (ParticParser.VariableDeclaratorContext varCtx : ctx.variableDeclarator()) {
            String fieldName = varCtx.IDENTIFIER().getText();
            ParticField field = new ParticField(resolvedType, fieldName);
            
            // 复制修饰符
            ParticModifiers fieldModifiers = new ParticModifiers();
            fieldModifiers.setAccess(modifiers.getAccess());
            for (String other : modifiers.getOthers()) {
                fieldModifiers.addOther(other);
            }
            field.setModifiers(fieldModifiers);
            
            // 添加注解
            for (ParticAnnotation annotation : annotations) {
                field.addAnnotation(annotation);
            }
            
            // 初始值(如果有)
            if (varCtx.expression() != null) {
                // TODO: 字段初始化表达式需要在类初始化时处理，暂时保存文本
                // 后续可能需要生成静态初始化块或实例初始化块
                field.setValue(varCtx.expression().getText());
            }
            
            fields.add(field);
        }
        
        return fields;
    }
}
