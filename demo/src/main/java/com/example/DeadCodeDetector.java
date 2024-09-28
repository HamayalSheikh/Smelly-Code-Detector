package com.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

public class DeadCodeDetector {

    public static void detectDeadCode(CompilationUnit cu) {
        Set<String> declaredMethods = new HashSet<>();
        Set<String> calledMethods = new HashSet<>();
        Set<String> declaredVariables = new HashSet<>();
        Set<String> usedVariables = new HashSet<>();

        // Find all method declarations (to check for unused methods)
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            declaredMethods.add(method.getNameAsString());
        });

        // Find all method calls (to mark methods as used)
        cu.findAll(MethodCallExpr.class).forEach(methodCall -> {
            calledMethods.add(methodCall.getNameAsString());
        });

        // Find all variable declarations (to check for unused variables)
        cu.findAll(VariableDeclarator.class).forEach(variable -> {
            declaredVariables.add(variable.getNameAsString());
        });

        // Find all variable usages (to mark variables as used)
        cu.findAll(NameExpr.class).forEach(varUsage -> {
            usedVariables.add(varUsage.getNameAsString());
        });

        // Check for unused methods
        declaredMethods.forEach(method -> {
            if (!calledMethods.contains(method)) {
                System.out.println("Dead code detected: Unused method - " + method);
            }
        });

        // Check for unused variables
        declaredVariables.forEach(variable -> {
            if (!usedVariables.contains(variable)) {
                System.out.println("Dead code detected: Unused variable - " + variable);
            }
        });
    }
}
