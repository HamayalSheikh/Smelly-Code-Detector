package com.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataOnlyClassDetector {

    public static void detectDataOnlyClasses(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
            List<MethodDeclaration> methods = clazz.getMethods();
            List<FieldDeclaration> fields = clazz.getFields();

            if (methods.isEmpty()) {
                return; // Skip classes with no methods
            }

            boolean allGettersSetters = true;
            Set<String> fieldNames = new HashSet<>();

            // Collect field names for comparison with method names
            fields.forEach(field -> {
                for (VariableDeclarator variable : field.getVariables()) {
                    fieldNames.add(variable.getNameAsString());
                }
            });

            // Check if all methods are either getters or setters
            for (MethodDeclaration method : methods) {
                if (!isGetterOrSetter(method, fieldNames)) {
                    allGettersSetters = false;
                    break;
                }
            }

            // If all methods are getters/setters, it is a data-only class
            if (allGettersSetters) {
                System.out.println("Data-Only Class detected: " + clazz.getNameAsString());
            }
        });
    }

    private static boolean isGetterOrSetter(MethodDeclaration method, Set<String> fieldNames) {
        String methodName = method.getNameAsString();

        // Check if the method is a getter
        if (methodName.startsWith("get") && method.getParameters().isEmpty()) {
            String fieldName = methodName.substring(3).toLowerCase(); // remove "get" and lowercase field
            return fieldNames.contains(fieldName);
        }

        // Check if the method is a setter
        if (methodName.startsWith("set") && method.getParameters().size() == 1) {
            String fieldName = methodName.substring(3).toLowerCase(); // remove "set" and lowercase field
            return fieldNames.contains(fieldName);
        }

        // If it's not a getter or setter, return false
        return false;
    }

}
