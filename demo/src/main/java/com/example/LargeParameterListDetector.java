package com.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class LargeParameterListDetector {

    public static void detectLargeParameterList(CompilationUnit cu, int maxParameters) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            if (method.getParameters().size() > maxParameters) {
                System.out.println("Large Parameter List in method: " + method.getName() + 
                                   ", Parameters: " + method.getParameters().size());
            }
        });
    }
}
