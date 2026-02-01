package lang.partic.compiler.symbol;

import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部类加载器 - 使用 ASM 读取 .class 文件元数据
 */
public class ExternalClassLoader {
    private static final Logger log = LoggerFactory.getLogger(ExternalClassLoader.class);
    
    // 缓存已加载的外部类
    private final Map<String, ClassSymbol> cache = new HashMap<>();
    private final SymbolTable symbolTable;

    public ExternalClassLoader(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    /**
     * 加载外部类（从 classpath）
     * @param className 完整类名，如 java.lang.System
     * @return ClassSymbol，如果找不到返回 null
     */
    public ClassSymbol loadClass(String className) {
        // 检查缓存
        if (cache.containsKey(className)) {
            return cache.get(className);
        }

        // 转换为资源路径: java.lang.System -> java/lang/System.class
        String resourcePath = className.replace('.', '/') + ".class";
        
        try (InputStream is = getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                log.debug("找不到外部类: {}", className);
                cache.put(className, null);
                return null;
            }

            ClassReader reader = new ClassReader(is);
            ClassSymbol classSymbol = new ClassSymbol(className, symbolTable.getGlobalScope());
            classSymbol.setFullName(className);

            // 使用 ASM 访问类结构
            ClassInfoVisitor visitor = new ClassInfoVisitor(classSymbol);
            reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
            
            // 加载并设置父类
            String superClassName = visitor.getSuperClassName();
            if (superClassName != null) {
                ClassSymbol superClass = loadClass(superClassName);
                if (superClass != null) {
                    classSymbol.setSuperClass(superClass);
                    log.debug("设置父类: {} -> {}", className, superClassName);
                } else {
                    log.warn("无法加载父类: {} (对于类 {})", superClassName, className);
                }
            }
            
            // 加载并设置接口
            String[] interfaceNames = visitor.getInterfaceNames();
            if (interfaceNames != null) {
                for (String interfaceName : interfaceNames) {
                    ClassSymbol interfaceSymbol = loadClass(interfaceName);
                    if (interfaceSymbol != null) {
                        classSymbol.addInterface(interfaceSymbol);
                        log.debug("设置接口: {} -> {}", className, interfaceName);
                    } else {
                        log.warn("无法加载接口: {} (对于类 {})", interfaceName, className);
                    }
                }
            }

            cache.put(className, classSymbol);
            log.debug("加载外部类: {} (字段={}, 方法={}, 父类={})", 
                className, 
                classSymbol.getFields().size(),
                classSymbol.getAllMethods().size(),
                superClassName);
            
            return classSymbol;
            
        } catch (IOException e) {
            log.warn("加载外部类失败: {}", className, e);
            cache.put(className, null);
            return null;
        }
    }

    /**
     * 解析类（优先符号表，其次外部加载）
     */
    public ClassSymbol resolveClass(String className) {
        // 1. 先查符号表（当前文件的类）
        ClassSymbol local = symbolTable.resolveClass(className);
        if (local != null) {
            return local;
        }
        
        // 2. 加载外部类
        return loadClass(className);
    }

    private ClassLoader getClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ExternalClassLoader.class.getClassLoader();
        }
        return cl;
    }

    /**
     * ASM ClassVisitor - 提取类的字段和方法信息
     */
    private class ClassInfoVisitor extends ClassVisitor {
        private final ClassSymbol classSymbol;

        public ClassInfoVisitor(ClassSymbol classSymbol) {
            super(Opcodes.ASM9);
            this.classSymbol = classSymbol;
        }

        private String superClassName; // 暂存父类名
        private String[] interfaceNames; // 暂存接口名
        
        @Override
        public void visit(int version, int access, String name, String signature, 
                          String superName, String[] interfaces) {
            classSymbol.setModifiers(access);
            
            // 记录父类名称（延迟加载，在需要时再解析）
            // 即使是 Object 也需要加载，因为其他类可能继承自 Object
            if (superName != null) {
                this.superClassName = superName.replace('/', '.');
                log.debug("ClassInfoVisitor.visit: 类 {} 的父类是 {}", classSymbol.getName(), this.superClassName);
            } else {
                this.superClassName = null;
                log.debug("ClassInfoVisitor.visit: 类 {} 没有父类", classSymbol.getName());
            }
            
            // 记录接口名称
            if (interfaces != null && interfaces.length > 0) {
                this.interfaceNames = new String[interfaces.length];
                for (int i = 0; i < interfaces.length; i++) {
                    this.interfaceNames[i] = interfaces[i].replace('/', '.');
                }
                log.debug("ClassInfoVisitor.visit: 类 {} 实现了接口 {}", 
                    classSymbol.getName(), String.join(", ", this.interfaceNames));
            }
        }
        
        public String getSuperClassName() {
            return superClassName;
        }
        
        public String[] getInterfaceNames() {
            return interfaceNames;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, 
                                        String signature, Object value) {
            String fieldType = descriptorToType(descriptor);
            FieldSymbol field = new FieldSymbol(name, fieldType, access);
            classSymbol.addField(field);
            return null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, 
                                          String signature, String[] exceptions) {
            // 跳过构造函数和静态初始化块（可选）
            if (name.equals("<clinit>")) {
                return null;
            }
            
            boolean isConstructor = name.equals("<init>");
            String returnType = getReturnType(descriptor);
            
            MethodSymbol method = new MethodSymbol(
                isConstructor ? classSymbol.getName() : name,
                returnType,
                isConstructor
            );
            method.setModifiers(access);
            
            // 检查是否为可变参数方法
            boolean isVarArgs = (access & Opcodes.ACC_VARARGS) != 0;
            method.setVarArgs(isVarArgs);
            
            // 解析参数类型
            String[] paramTypes = getParameterTypes(descriptor);
            for (int i = 0; i < paramTypes.length; i++) {
                method.addParameter("arg" + i, paramTypes[i]);
            }
            
            // 调试：打印 nextDouble 方法
            if (name.equals("nextDouble")) {
                log.debug("发现方法: {} {} {} (access={}, public={})", 
                    classSymbol.getName(), name, descriptor, 
                    access, (access & Opcodes.ACC_PUBLIC) != 0);
            }
            
            if (isConstructor) {
                classSymbol.addConstructor(method);
            } else {
                classSymbol.addMethod(method);
            }
            
            return null;
        }
    }

    // ============ 描述符解析 ============

    /**
     * 将字段描述符转换为类型名
     * I -> int, Ljava/lang/String; -> java.lang.String
     */
    private String descriptorToType(String descriptor) {
        return parseType(descriptor, 0).type;
    }

    /**
     * 获取方法返回类型
     */
    private String getReturnType(String descriptor) {
        int idx = descriptor.lastIndexOf(')');
        return descriptorToType(descriptor.substring(idx + 1));
    }

    /**
     * 获取方法参数类型列表
     */
    private String[] getParameterTypes(String descriptor) {
        // (ILjava/lang/String;)V -> ["int", "java.lang.String"]
        int start = 1;  // 跳过 '('
        int end = descriptor.indexOf(')');
        
        java.util.List<String> params = new java.util.ArrayList<>();
        int idx = start;
        
        while (idx < end) {
            TypeParseResult result = parseType(descriptor, idx);
            params.add(result.type);
            idx = result.nextIndex;
        }
        
        return params.toArray(new String[0]);
    }

    /**
     * 解析单个类型
     */
    private TypeParseResult parseType(String descriptor, int idx) {
        char c = descriptor.charAt(idx);
        
        return switch (c) {
            case 'V' -> new TypeParseResult("void", idx + 1);
            case 'Z' -> new TypeParseResult("bool", idx + 1);  // Partic 使用 bool
            case 'B' -> new TypeParseResult("byte", idx + 1);
            case 'C' -> new TypeParseResult("char", idx + 1);
            case 'S' -> new TypeParseResult("short", idx + 1);
            case 'I' -> new TypeParseResult("int", idx + 1);
            case 'J' -> new TypeParseResult("long", idx + 1);
            case 'F' -> new TypeParseResult("float", idx + 1);
            case 'D' -> new TypeParseResult("double", idx + 1);
            case 'L' -> {
                // 对象类型: Ljava/lang/String;
                int semicolon = descriptor.indexOf(';', idx);
                String className = descriptor.substring(idx + 1, semicolon).replace('/', '.');
                yield new TypeParseResult(className, semicolon + 1);
            }
            case '[' -> {
                // 数组类型
                TypeParseResult element = parseType(descriptor, idx + 1);
                yield new TypeParseResult(element.type + "[]", element.nextIndex);
            }
            default -> new TypeParseResult("unknown", idx + 1);
        };
    }

    private record TypeParseResult(String type, int nextIndex) {}

    /**
     * 清除缓存
     */
    public void clearCache() {
        cache.clear();
    }
}
