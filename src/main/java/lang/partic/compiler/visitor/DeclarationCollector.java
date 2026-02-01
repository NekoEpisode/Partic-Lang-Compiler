package lang.partic.compiler.visitor;

import lang.partic.compiler.antlr.ParticBaseVisitor;
import lang.partic.compiler.antlr.ParticParser;
import lang.partic.compiler.manager.ImportManager;
import lang.partic.compiler.symbol.ClassSymbol;
import lang.partic.compiler.symbol.FieldSymbol;
import lang.partic.compiler.symbol.MethodSymbol;
import lang.partic.compiler.symbol.SymbolTable;

import java.lang.reflect.Modifier;

/**
 * Pass 1: 声明收集器
 * 
 * 遍历 AST 收集所有类、字段、方法的声明（不处理方法体）
 * 用于建立符号表，支持前向引用
 */
public class DeclarationCollector extends ParticBaseVisitor<Void> {
    private final SymbolTable symbolTable;
    private final ImportManager importManager;
    private ClassSymbol currentClass;  // 当前正在处理的类

    public DeclarationCollector(SymbolTable symbolTable, ImportManager importManager) {
        this.symbolTable = symbolTable;
        this.importManager = importManager;
    }

    /**
     * 收集程序中的所有声明
     */
    @Override
    public Void visitProgram(ParticParser.ProgramContext ctx) {
        // 第一遍：收集所有类名（支持同一文件内的前向引用）
        for (ParticParser.TypeDeclarationContext typeCtx : ctx.typeDeclaration()) {
            if (typeCtx.classDeclaration() != null) {
                String className = typeCtx.classDeclaration().IDENTIFIER().getText();
                ClassSymbol classSymbol = new ClassSymbol(className, symbolTable.getGlobalScope());
                symbolTable.defineClass(classSymbol);
            }
            // TODO: 处理 interface 和 enum
        }

        // 第二遍：收集类的成员（字段、方法）
        for (ParticParser.TypeDeclarationContext typeCtx : ctx.typeDeclaration()) {
            if (typeCtx.classDeclaration() != null) {
                visitClassDeclaration(typeCtx.classDeclaration());
            }
        }

        return null;
    }

    @Override
    public Void visitClassDeclaration(ParticParser.ClassDeclarationContext ctx) {
        String className = ctx.IDENTIFIER().getText();
        currentClass = symbolTable.resolveClass(className);

        if (currentClass == null) {
            // 不应该发生，第一遍已经收集了所有类名
            throw new IllegalStateException("Class not found in symbol table: " + className);
        }

        // 设置修饰符
        int modifiers = parseModifiers(ctx.modifiers());
        currentClass.setModifiers(modifiers);

        // 设置父类
        if (ctx.type() != null) {
            String superClassName = resolveType(ctx.type().getText());
            // TODO: 解析父类符号（可能在其他文件中）
            // 目前只保存名称
            currentClass.setType(superClassName);  // 临时用 type 字段存父类
        }

        // 设置实现的接口
        if (ctx.typeList() != null) {
            for (ParticParser.TypeContext typeCtx : ctx.typeList().type()) {
                String interfaceName = resolveType(typeCtx.getText());
                // TODO: 解析接口符号
            }
        }

        // 进入类作用域
        symbolTable.enterScope(currentClass.getMemberScope());

        // 收集类成员
        if (ctx.classBody() != null) {
            for (ParticParser.ClassBodyDeclarationContext bodyCtx : ctx.classBody().classBodyDeclaration()) {
                if (bodyCtx.fieldDeclaration() != null) {
                    visitFieldDeclaration(bodyCtx.fieldDeclaration());
                } else if (bodyCtx.methodDeclaration() != null) {
                    visitMethodDeclaration(bodyCtx.methodDeclaration());
                } else if (bodyCtx.constructorDeclaration() != null) {
                    visitConstructorDeclaration(bodyCtx.constructorDeclaration());
                }
                // staticBlock 暂时忽略
            }
        }

        // 退出类作用域
        symbolTable.exitScope();
        currentClass = null;

        return null;
    }

    @Override
    public Void visitFieldDeclaration(ParticParser.FieldDeclarationContext ctx) {
        String typeName = ctx.type().getText();
        String resolvedType = resolveType(typeName);
        int modifiers = parseModifiers(ctx.modifiers());

        // 一个字段声明可以声明多个变量: int a, b, c;
        for (ParticParser.VariableDeclaratorContext varCtx : ctx.variableDeclarator()) {
            String fieldName = varCtx.IDENTIFIER().getText();
            FieldSymbol field = new FieldSymbol(fieldName, resolvedType, modifiers);
            currentClass.addField(field);
        }

        return null;
    }

    @Override
    public Void visitMethodDeclaration(ParticParser.MethodDeclarationContext ctx) {
        String methodName = ctx.IDENTIFIER().getText();
        String returnType = resolveType(ctx.type().getText());
        int modifiers = parseModifiers(ctx.modifiers());

        MethodSymbol method = new MethodSymbol(methodName, returnType, false);
        method.setModifiers(modifiers);

        // 收集参数
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                String paramName = paramCtx.IDENTIFIER().getText();
                String paramType = resolveType(paramCtx.type().getText());
                method.addParameter(paramName, paramType);
            }
        }

        // 创建方法体作用域（但不处理方法体内容）
        method.createBodyScope(currentClass.getMemberScope());

        currentClass.addMethod(method);
        return null;
    }

    @Override
    public Void visitConstructorDeclaration(ParticParser.ConstructorDeclarationContext ctx) {
        String ctorName = ctx.IDENTIFIER().getText();
        int modifiers = parseModifiers(ctx.modifiers());

        // 构造函数返回类型是类本身
        MethodSymbol constructor = new MethodSymbol(ctorName, currentClass.getName(), true);
        constructor.setModifiers(modifiers);

        // 收集参数
        if (ctx.parameterList() != null) {
            for (ParticParser.ParameterContext paramCtx : ctx.parameterList().parameter()) {
                String paramName = paramCtx.IDENTIFIER().getText();
                String paramType = resolveType(paramCtx.type().getText());
                constructor.addParameter(paramName, paramType);
            }
        }

        // 创建方法体作用域
        constructor.createBodyScope(currentClass.getMemberScope());

        currentClass.addConstructor(constructor);
        return null;
    }

    // ============ 辅助方法 ============

    /**
     * 解析类型名为完整类名
     */
    private String resolveType(String typeName) {
        // 处理数组类型
        String arrayDim = "";
        String baseType = typeName;
        while (baseType.endsWith("[]")) {
            arrayDim += "[]";
            baseType = baseType.substring(0, baseType.length() - 2);
        }

        // 尝试从 ImportManager 解析
        String resolved = importManager.findFullName(baseType);
        if (resolved != null) {
            return resolved + arrayDim;
        }

        // 尝试从当前文件的类中查找
        ClassSymbol localClass = symbolTable.resolveClass(baseType);
        if (localClass != null) {
            return localClass.getFullName() + arrayDim;
        }

        // 返回原始名称（可能是完整类名或未解析的类型）
        return typeName;
    }

    /**
     * 解析修饰符
     */
    private int parseModifiers(ParticParser.ModifiersContext ctx) {
        int modifiers = 0;
        if (ctx == null) return modifiers;

        for (ParticParser.ModifierContext modCtx : ctx.modifier()) {
            String mod = modCtx.getText();
            modifiers |= switch (mod) {
                case "pub" -> Modifier.PUBLIC;
                case "priv" -> Modifier.PRIVATE;
                case "prot" -> Modifier.PROTECTED;
                case "pack" -> 0;  // package-private，默认
                case "static" -> Modifier.STATIC;
                case "final" -> Modifier.FINAL;
                case "abstract" -> Modifier.ABSTRACT;
                case "native" -> Modifier.NATIVE;
                case "synchronized" -> Modifier.SYNCHRONIZED;
                case "transient" -> Modifier.TRANSIENT;
                case "volatile" -> Modifier.VOLATILE;
                default -> 0;
            };
        }

        return modifiers;
    }

    // ============ Getters ============

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
