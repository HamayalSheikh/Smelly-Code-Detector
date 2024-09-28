package com.example;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;


public class LongMethodDetector {
    public static void detectLongMethods(CompilationUnit cu, int threshold) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int length = method.getEnd().get().line - method.getBegin().get().line;
            if (length > threshold) {
                System.out.println("Long method found: " + method.getName() + " with " + length + " lines.");
            }
        });
    }
}
