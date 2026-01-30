package lang.partic.compiler.visitor.classvisitors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.manager.ImportManager;

public class MethodDeclarationVisitor extends ParticBaseVisitor<JsonObject> {
    private final VisitContext context;

    public MethodDeclarationVisitor(VisitContext context) {
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
    public JsonObject visitMethodDeclaration(ParticParser.MethodDeclarationContext ctx) {
        JsonObject method = new JsonObject();
        
        // 方法修饰符 - 按照IR格式分为access和others
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
        
        JsonObject methodModifiers = new JsonObject();
        methodModifiers.addProperty("access", access);
        methodModifiers.add("others", others);
        method.add("modifiers", methodModifiers);
        
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
        method.add("annotations", annotations);
        
        // 返回类型 - 解析为完整类名
        String returnType = ctx.type().getText();
        String resolvedReturnType = resolveType(returnType);
        method.addProperty("return_type", resolvedReturnType);
        
        // 参数
        JsonArray params = new JsonArray();
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                JsonObject param = new JsonObject();
                
                // 参数类型 - 解析为完整类名
                String paramType = paramCtx.type().getText();
                String resolvedParamType = resolveType(paramType);
                param.addProperty("type", resolvedParamType);
                param.addProperty("name", paramCtx.IDENTIFIER().getText());
                
                // 参数注解
                if (!paramCtx.annotation().isEmpty()) {
                    JsonArray paramAnnotations = new JsonArray();
                    for (ParticParser.AnnotationContext annCtx : paramCtx.annotation()) {
                        JsonObject annotation = new JsonObject();
                        String annotationType = annCtx.qualifiedName().getText();
                        String resolvedAnnotation = resolveType(annotationType);
                        annotation.addProperty("annotation", resolvedAnnotation);
                        
                        if (annCtx.elementValuePairs() != null) {
                            JsonArray args = new JsonArray();
                            // TODO: 解析注解参数
                            annotation.add("args", args);
                        }
                        
                        paramAnnotations.add(annotation);
                    }
                    param.add("annotations", paramAnnotations);
                }
                
                params.add(param);
            }
        }
        method.add("parameters", params);
        
        // 方法体
        if (ctx.block() != null) {
            JsonObject body = new JsonObject();
            body.add("locals", new JsonObject());
            body.add("temps", new JsonObject());
            body.add("statements", new JsonArray());
            method.add("body", body);
            // TODO: 处理方法体语句
        }
        
        return method;
    }
}
