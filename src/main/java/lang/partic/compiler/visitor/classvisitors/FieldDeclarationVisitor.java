package lang.partic.compiler.visitor.classvisitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.manager.ImportManager;

public class FieldDeclarationVisitor extends ParticBaseVisitor<JsonArray> {
    private final VisitContext context;

    public FieldDeclarationVisitor(VisitContext context) {
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
    public JsonArray visitFieldDeclaration(ParticParser.FieldDeclarationContext ctx) {
        JsonArray fields = new JsonArray();
        
        // 提取公共信息
        // 字段修饰符 - 按照IR格式分为access和others
        String access = "pub"; // 默认public
        JsonArray others = new JsonArray();
        
        if (ctx.modifiers() != null) {
            for (ParticParser.ModifierContext modCtx : ctx.modifiers().modifier()) {
                String mod = modCtx.getText();
                // 访问修饰符：pub, priv, prot, pack
                switch (mod) {
                    case "pub", "priv", "prot", "pack" -> access = mod;
                    default -> others.add(mod);
                }
            }
        }
        
        JsonObject fieldModifiers = new JsonObject();
        fieldModifiers.addProperty("access", access);
        fieldModifiers.add("others", others);
        
        // 字段类型 - 解析为完整类名
        String fieldType = ctx.type().getText();
        String resolvedType = resolveType(fieldType);
        
        // 注解
        JsonArray annotations = new JsonArray();
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            JsonObject annotation = new JsonObject();
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = resolveType(annotationType);
            annotation.addProperty("annotation", resolvedAnnotation);
            
            if (annCtx.elementValuePairs() != null) {
                JsonArray args = new JsonArray();
                // TODO: 解析注解参数
                annotation.add("args", args);
            }
            
            annotations.add(annotation);
        }
        
        // 处理每个变量声明
        for (ParticParser.VariableDeclaratorContext varCtx : ctx.variableDeclarator()) {
            JsonObject field = new JsonObject();
            field.add("modifiers", fieldModifiers.deepCopy());
            field.addProperty("type", resolvedType);
            field.addProperty("name", varCtx.IDENTIFIER().getText());
            
            // 初始值(如果有)
            if (varCtx.expression() != null) {
                // TODO: 解析表达式值
                field.addProperty("value", varCtx.expression().getText());
            }
            
            // 注解(如果有)
            if (!annotations.isEmpty()) {
                field.add("annotations", annotations.deepCopy());
            }
            
            fields.add(field);
        }
        
        return fields;
    }
}
