package lang.partic.compiler.ir;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lang.partic.compiler.StaticValues;

import java.time.Instant;
import java.util.Map;

/**
 * 将中间表示（ParticProgram等）转换为JSON IR
 * 这个类不是Visitor，而是专门负责IR生成
 */
public class ParticIRGenerator {

    /**
     * 生成完整的IR JSON
     */
    public JsonObject generate(ParticProgram program) {
        JsonObject ir = new JsonObject();
        
        // metadata
        JsonObject metadata = new JsonObject();
        metadata.addProperty("generated", Instant.now().toString());
        metadata.addProperty("ir_version", StaticValues.IR_VERSION);
        metadata.addProperty("source_file", program.getSourceFile());
        ir.add("metadata", metadata);
        
        // classes
        JsonArray classes = new JsonArray();
        for (ParticClass particClass : program.getClasses()) {
            classes.add(generateClass(particClass));
        }
        ir.add("classes", classes);
        
        return ir;
    }

    /**
     * 生成类的IR
     */
    private JsonObject generateClass(ParticClass particClass) {
        JsonObject classObj = new JsonObject();
        
        classObj.addProperty("name", particClass.getName());
        classObj.add("modifiers", generateModifiers(particClass.getModifiers()));
        
        // implements
        JsonArray implementsArray = new JsonArray();
        for (String interfaceName : particClass.getImplementsList()) {
            implementsArray.add(interfaceName);
        }
        classObj.add("implements", implementsArray);
        
        // annotations
        JsonArray annotations = new JsonArray();
        for (ParticAnnotation annotation : particClass.getAnnotations()) {
            annotations.add(generateAnnotation(annotation));
        }
        classObj.add("annotations", annotations);
        
        // type_parameters
        JsonArray typeParams = new JsonArray();
        for (String typeParam : particClass.getTypeParameters()) {
            typeParams.add(typeParam);
        }
        classObj.add("type_parameters", typeParams);
        
        classObj.addProperty("extends", particClass.getExtendsClass());
        
        // fields
        JsonArray fields = new JsonArray();
        for (ParticField field : particClass.getFields()) {
            fields.add(generateField(field));
        }
        classObj.add("fields", fields);
        
        // methods
        JsonObject methods = new JsonObject();
        for (Map.Entry<String, ParticMethod> entry : particClass.getMethods().entrySet()) {
            methods.add(entry.getKey(), generateMethod(entry.getValue()));
        }
        classObj.add("methods", methods);
        
        // constructors
        JsonArray constructors = new JsonArray();
        for (ParticConstructor constructor : particClass.getConstructors()) {
            constructors.add(generateConstructor(constructor));
        }
        classObj.add("constructors", constructors);
        
        return classObj;
    }

    /**
     * 生成修饰符的IR
     */
    private JsonObject generateModifiers(ParticModifiers modifiers) {
        JsonObject modObj = new JsonObject();
        modObj.addProperty("access", modifiers.getAccess());
        
        JsonArray others = new JsonArray();
        for (String other : modifiers.getOthers()) {
            others.add(other);
        }
        modObj.add("others", others);
        
        return modObj;
    }

    /**
     * 生成注解的IR
     */
    private JsonObject generateAnnotation(ParticAnnotation annotation) {
        JsonObject annObj = new JsonObject();
        annObj.addProperty("annotation", annotation.getAnnotation());
        
        if (!annotation.getArgs().isEmpty()) {
            JsonArray args = new JsonArray();
            for (ParticAnnotation.AnnotationArg arg : annotation.getArgs()) {
                JsonObject argObj = new JsonObject();
                argObj.addProperty("name", arg.getName());
                argObj.add("value", convertValue(arg.getValue()));
                argObj.addProperty("type", arg.getType());
                
                if (!arg.getAnnotations().isEmpty()) {
                    JsonArray argAnnotations = new JsonArray();
                    for (ParticAnnotation argAnn : arg.getAnnotations()) {
                        argAnnotations.add(generateAnnotation(argAnn));
                    }
                    argObj.add("annotations", argAnnotations);
                }
                
                args.add(argObj);
            }
            annObj.add("args", args);
        }
        
        return annObj;
    }

    /**
     * 生成字段的IR
     */
    private JsonObject generateField(ParticField field) {
        JsonObject fieldObj = new JsonObject();
        fieldObj.add("modifiers", generateModifiers(field.getModifiers()));
        fieldObj.addProperty("type", field.getType());
        fieldObj.addProperty("name", field.getName());
        
        if (field.getValue() != null) {
            fieldObj.add("value", convertValue(field.getValue()));
        }
        
        return fieldObj;
    }

    /**
     * 生成方法的IR
     */
    private JsonObject generateMethod(ParticMethod method) {
        JsonObject methodObj = new JsonObject();
        
        methodObj.add("modifiers", generateModifiers(method.getModifiers()));
        
        // annotations
        JsonArray annotations = new JsonArray();
        for (ParticAnnotation annotation : method.getAnnotations()) {
            annotations.add(generateAnnotation(annotation));
        }
        methodObj.add("annotations", annotations);
        
        methodObj.addProperty("return_type", method.getReturnType());
        
        // parameters
        JsonArray params = new JsonArray();
        for (ParticParameter param : method.getParameters()) {
            params.add(generateParameter(param));
        }
        methodObj.add("parameters", params);
        
        // body
        if (method.getBody() != null) {
            methodObj.add("body", generateMethodBody(method.getBody()));
        }
        
        return methodObj;
    }

    /**
     * 生成参数的IR
     */
    private JsonObject generateParameter(ParticParameter param) {
        JsonObject paramObj = new JsonObject();
        paramObj.addProperty("type", param.getType());
        paramObj.addProperty("name", param.getName());
        
        if (!param.getAnnotations().isEmpty()) {
            JsonArray annotations = new JsonArray();
            for (ParticAnnotation annotation : param.getAnnotations()) {
                annotations.add(generateAnnotation(annotation));
            }
            paramObj.add("annotations", annotations);
        }
        
        return paramObj;
    }

    /**
     * 生成构造函数的IR
     */
    private JsonObject generateConstructor(ParticConstructor constructor) {
        JsonObject ctorObj = new JsonObject();
        
        ctorObj.add("modifiers", generateModifiers(constructor.getModifiers()));
        
        // annotations
        JsonArray annotations = new JsonArray();
        for (ParticAnnotation annotation : constructor.getAnnotations()) {
            annotations.add(generateAnnotation(annotation));
        }
        ctorObj.add("annotations", annotations);
        
        // parameters
        JsonArray params = new JsonArray();
        for (ParticParameter param : constructor.getParameters()) {
            params.add(generateParameter(param));
        }
        ctorObj.add("parameters", params);
        
        // body
        if (constructor.getBody() != null) {
            ctorObj.add("body", generateMethodBody(constructor.getBody()));
        }
        
        return ctorObj;
    }

    /**
     * 生成方法体的IR
     */
    private JsonObject generateMethodBody(ParticMethodBody body) {
        JsonObject bodyObj = new JsonObject();
        
        // locals
        JsonObject locals = new JsonObject();
        for (Map.Entry<String, ParticMethodBody.LocalVar> entry : body.getLocals().entrySet()) {
            JsonObject localObj = new JsonObject();
            localObj.addProperty("type", entry.getValue().getType());
            localObj.addProperty("scope", entry.getValue().getScopeLevel());
            
            // modifiers
            JsonArray modifiers = new JsonArray();
            for (String mod : entry.getValue().getModifiers()) {
                modifiers.add(mod);
            }
            localObj.add("modifiers", modifiers);
            
            locals.add(entry.getKey(), localObj);
        }
        bodyObj.add("locals", locals);
        
        // temps
        JsonObject temps = new JsonObject();
        for (Map.Entry<String, ParticMethodBody.TempVar> entry : body.getTemps().entrySet()) {
            temps.add(entry.getKey(), generateTempVar(entry.getValue()));
        }
        bodyObj.add("temps", temps);
        
        // statements
        JsonArray statements = new JsonArray();
        for (ParticMethodBody.Statement stmt : body.getStatements()) {
            statements.add(generateStatement(stmt));
        }
        bodyObj.add("statements", statements);
        
        return bodyObj;
    }

    /**
     * 生成临时变量的IR
     */
    private JsonObject generateTempVar(ParticMethodBody.TempVar temp) {
        JsonObject tempObj = new JsonObject();
        tempObj.addProperty("op", temp.getOp());
        
        if (temp.getValue() != null) {
            tempObj.add("value", convertValue(temp.getValue()));
        }
        
        if (!temp.getOperands().isEmpty()) {
            JsonArray operands = new JsonArray();
            for (String operand : temp.getOperands()) {
                operands.add(operand);
            }
            tempObj.add("operands", operands);
        }
        
        // metadata (如object, field, method等)
        for (Map.Entry<String, Object> entry : temp.getMetadata().entrySet()) {
            tempObj.add(entry.getKey(), convertValue(entry.getValue()));
        }
        
        return tempObj;
    }

    /**
     * 生成语句的IR
     */
    private JsonObject generateStatement(ParticMethodBody.Statement stmt) {
        JsonObject stmtObj = new JsonObject();
        stmtObj.addProperty("op", stmt.getOp());
        
        if (stmt instanceof ParticMethodBody.AssignStatement assign) {
            stmtObj.addProperty("target", assign.getTarget());
            stmtObj.addProperty("value", assign.getValue());
        } else if (stmt instanceof ParticMethodBody.ReturnStatement ret) {
            stmtObj.addProperty("value", ret.getValue());
        } else if (stmt instanceof ParticMethodBody.CallStatement call) {
            if (call.getObject() != null) {
                stmtObj.addProperty("object", call.getObject());
            }
            stmtObj.addProperty("method", call.getMethod());
            
            JsonArray signature = new JsonArray();
            for (String type : call.getSignature()) {
                signature.add(type);
            }
            stmtObj.add("signature", signature);
            
            JsonArray args = new JsonArray();
            for (String arg : call.getArgs()) {
                args.add(arg);
            }
            stmtObj.add("args", args);
        } else if (stmt instanceof ParticMethodBody.ScopeStatement scope) {
            stmtObj.addProperty("scope_id", scope.getScopeId());
        }
        // TODO: 处理其他语句类型
        
        return stmtObj;
    }

    /**
     * 将Java值转换为JsonElement
     */
    private com.google.gson.JsonElement convertValue(Object value) {
        if (value instanceof String) {
            return new com.google.gson.JsonPrimitive((String) value);
        } else if (value instanceof Number) {
            return new com.google.gson.JsonPrimitive((Number) value);
        } else if (value instanceof Boolean) {
            return new com.google.gson.JsonPrimitive((Boolean) value);
        }
        return com.google.gson.JsonNull.INSTANCE;
    }
}
