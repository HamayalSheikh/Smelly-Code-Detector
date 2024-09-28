package com.example;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;

public class DuplicatedCodeDetector {

    public static void detectDuplicatedCode(CompilationUnit cu) {
        Set<String> methodBodies = new HashSet<>();
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            String body = method.getBody().toString();
            if (!methodBodies.add(body)) {
                System.out.println("Duplicated Method detected: " + method.getName());
            }
        });
    }
}

