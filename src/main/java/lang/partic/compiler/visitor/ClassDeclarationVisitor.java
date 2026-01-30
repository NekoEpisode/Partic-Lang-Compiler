package lang.partic.compiler.visitor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.context.VisitContext;
import lang.partic.compiler.exceptions.CompileError;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.visitor.classvisitors.ConstructorDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.FieldDeclarationVisitor;
import lang.partic.compiler.visitor.classvisitors.MethodDeclarationVisitor;

public class ClassDeclarationVisitor extends ParticBaseVisitor<JsonObject> {
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
    public JsonObject visitClassDeclaration(ParticParser.ClassDeclarationContext ctx) {
        JsonObject classIR = new JsonObject();

        // 类名
        classIR.addProperty("name", ctx.IDENTIFIER().getText());



        // 修饰符
        JsonObject modifiersObj = new JsonObject();
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
        modifiersObj.addProperty("access", access);
        modifiersObj.add("others", others);
        classIR.add("modifiers", modifiersObj);

        // 实现的接口
        JsonArray interfaces = new JsonArray();
        if (ctx.typeList() != null) {
            for (ParticParser.TypeContext typeCtx : ctx.typeList().type()) {
                String interfaceType = typeCtx.getText();
                String resolvedType = resolveType(interfaceType);
                interfaces.add(resolvedType);
            }
        }
        classIR.add("implements", interfaces);

        // 注解
        JsonArray annotations = new JsonArray();
        for (ParticParser.AnnotationContext annCtx : ctx.annotation()) {
            JsonObject annotation = new JsonObject();
            String annotationType = annCtx.qualifiedName().getText();
            String resolvedAnnotation = resolveType(annotationType);
            annotation.addProperty("annotation", resolvedAnnotation);
            
            // 处理注解参数
            if (annCtx.elementValuePairs() != null) {
                JsonArray args = new JsonArray();
                // TODO: 解析注解参数
                annotation.add("args", args);
            }
            
            annotations.add(annotation);
        }
        classIR.add("annotations", annotations);

        // 类型参数 (泛型)
        // TODO: 实现泛型
        JsonArray typeParams = new JsonArray();
        classIR.add("type_parameters", typeParams);

        // 父类
        String extendsClass = "java.lang.Object"; // 默认继承Object
        if (ctx.type() != null) {
            String parentType = ctx.type().getText();
            extendsClass = resolveType(parentType);
        }
        classIR.addProperty("extends", extendsClass);

        // 字段
        JsonArray fields = new JsonArray();
        
        // 方法
        JsonObject methods = new JsonObject();

        // 构造函数
        JsonArray constructors = new JsonArray();

        // 处理类体
        if (ctx.classBody() != null) {
            for (ParticParser.ClassBodyDeclarationContext bodyCtx : ctx.classBody().classBodyDeclaration()) {
                if (bodyCtx.fieldDeclaration() != null) {
                    // 处理字段 - FieldVisitor 返回该声明中的所有字段
                    JsonArray declaredFields = fieldVisitor.visitFieldDeclaration(bodyCtx.fieldDeclaration());
                    for (int i = 0; i < declaredFields.size(); i++) {
                        fields.add(declaredFields.get(i));
                    }
                } else if (bodyCtx.methodDeclaration() != null) {
                    // 处理方法
                    ParticParser.MethodDeclarationContext methodCtx = bodyCtx.methodDeclaration();
                    String methodName = methodCtx.IDENTIFIER().getText();
                    JsonObject method = methodVisitor.visitMethodDeclaration(methodCtx);
                    methods.add(methodName, method);
                } else if (bodyCtx.constructorDeclaration() != null) {
                    // 处理构造函数
                    ParticParser.ConstructorDeclarationContext constructorCtx = bodyCtx.constructorDeclaration();
                    JsonObject constructor = constructorVisitor.visitConstructorDeclaration(constructorCtx);
                    constructors.add(constructor);
                }
            }
        }

        classIR.add("fields", fields);
        classIR.add("methods", methods);
        classIR.add("constructors", constructors);

        return classIR;
    }
}
