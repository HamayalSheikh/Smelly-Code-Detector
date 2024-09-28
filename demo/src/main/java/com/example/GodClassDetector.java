package com.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
public class GodClassDetector {
    public static void detectGodClasses(CompilationUnit cu, int methodThreshold, int fieldThreshold) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
            int methodCount = cls.getMethods().size();
            int fieldCount = cls.getFields().size();
            if (methodCount > methodThreshold || fieldCount > fieldThreshold) {
                System.out.println("God class detected: " + cls.getName());
            }
        });
    }
}
