package com.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MethodCall {
    private String calledClass;
    private String methodName;

    public MethodCall(String calledClass, String methodName) {
        this.calledClass = calledClass;
        this.methodName = methodName;
    }

    public String getCalledClass() {
        return calledClass;
    }

    public String getMethodName() {
        return methodName;
    }
}

class VariableAccess {
    private String variableClass;
    private String variableName;

    public VariableAccess(String variableClass, String variableName) {
        this.variableClass = variableClass;
        this.variableName = variableName;
    }

    public String getVariableClass() {
        return variableClass;
    }

    public String getVariableName() {
        return variableName;
    }
}

class MethodRepresentation {
    private String methodName;
    private List<MethodCall> methodCalls;
    private List<VariableAccess> variableAccesses;

    public MethodRepresentation(String methodName, List<MethodCall> methodCalls, List<VariableAccess> variableAccesses) {
        this.methodName = methodName;
        this.methodCalls = methodCalls;
        this.variableAccesses = variableAccesses;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<MethodCall> getMethodCalls() {
        return methodCalls;
    }

    public List<VariableAccess> getVariableAccesses() {
        return variableAccesses;
    }
}

class ClassRepresentation {
    private String name;
    private List<MethodRepresentation> methods;

    public ClassRepresentation(String name, List<MethodRepresentation> methods) {
        this.name = name;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public List<MethodRepresentation> getMethods() {
        return methods;
    }
}

// Feature Envy Detector class
public class FeatureEnvyDetector {

    public static void detectFeatureEnvy(CompilationUnit compilationUnit) {
        compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
            String className = clazz.getNameAsString();

            clazz.findAll(MethodDeclaration.class).forEach(method -> {
                String methodName = method.getNameAsString();
                Map<String, Integer> externalClassUsage = new HashMap<>();
                final int[] ownClassUsage = {0};

                method.findAll(MethodCallExpr.class).forEach(methodCall -> {
                    String calledClass = getCalledClass(methodCall);
                    if (calledClass.equals(className)) {
                        ownClassUsage[0]++;
                    } else {
                        externalClassUsage.put(calledClass, externalClassUsage.getOrDefault(calledClass, 0) + 1);
                    }
                });

                method.findAll(NameExpr.class).forEach(variableAccess -> {
                    String accessedClass = getAccessedClass(variableAccess);
                    if (accessedClass.equals(className)) {
                        ownClassUsage[0]++;
                    } else {
                        externalClassUsage.put(accessedClass, externalClassUsage.getOrDefault(accessedClass, 0) + 1);
                    }
                });

                externalClassUsage.forEach((externalClass, count) -> {
                    if (count > ownClassUsage[0]) {
                        System.out.println("Feature Envy detected in method '" + methodName +
                            "' of class '" + className + "' towards class '" + externalClass + "'");
                    }
                });
            });
        });
    }

    private static String getCalledClass(MethodCallExpr methodCall) {
        return methodCall.getScope().map(scope -> scope.toString()).orElse("ExternalClass");
    }

    private static String getAccessedClass(NameExpr variableAccess) {
        if (variableAccess.getNameAsString().startsWith("this.")) {
            return "CurrentClass"; // This should be the actual class name in context
        }
        return "ExternalClass";
    }
}
