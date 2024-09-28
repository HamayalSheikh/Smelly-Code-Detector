// package com.example;

// import com.github.javaparser.ast.body.MethodDeclaration;

// import java.util.HashSet;
// import java.util.Set;
// import java.util.concurrent.atomic.AtomicInteger;

// import com.github.javaparser.ast.CompilationUnit;

// public class DuplicatedCodeDetector {
//     public static void detectDuplicatedCode(CompilationUnit cu) {
//         Set<String> methodBodies = new HashSet<>();
//         AtomicInteger duplicatesCount = new AtomicInteger(0);
        
//         cu.findAll(MethodDeclaration.class).forEach(method -> {
//             // Normalize the method body (remove whitespaces and comments)
//             String body = method.getBody().map(Object::toString).orElse("")
//                 .replaceAll("\\s+", "") // Remove all whitespace
//                 .replaceAll("//.*|/\\*.*\\*/", ""); // Remove comments
            
//             if (!methodBodies.add(body)) {
//                 duplicatesCount.incrementAndGet();
//                 System.out.println("Duplicated Method detected: " + method.getName());
//             }
//         });
        
//         System.out.println("Total duplicated methods detected: " + duplicatesCount.get());
//     }
// }

package com.example;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.IfStmt;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javaparser.ast.CompilationUnit;

public class DuplicatedCodeDetector {

    public static void detectDuplicatedCode(CompilationUnit cu) {
        Set<String> methodBodies = new HashSet<>();
        Set<String> ifConditions = new HashSet<>();
        AtomicInteger duplicatesCount = new AtomicInteger(0);
        AtomicInteger duplicateIfCount = new AtomicInteger(0);

        cu.findAll(MethodDeclaration.class).forEach(method -> {
            // Normalize the method body (remove whitespaces and comments)
            String body = method.getBody().map(Object::toString).orElse("")
                .replaceAll("\\s+", "") // Remove all whitespace
                .replaceAll("//.*|/\\*.*\\*/", ""); // Remove comments

            if (!methodBodies.add(body)) {
                duplicatesCount.incrementAndGet();
                System.out.println("Duplicated Method detected: " + method.getName());
            }

            // Now check for duplicate if conditions within the method
            method.findAll(IfStmt.class).forEach(ifStmt -> {
                // Normalize the if condition (remove whitespaces and comments)
                String ifCondition = ifStmt.getCondition().toString()
                    .replaceAll("\\s+", "") // Remove all whitespace
                    .replaceAll("//.*|/\\*.*\\*/", ""); // Remove comments

                if (!ifConditions.add(ifCondition)) {
                    duplicateIfCount.incrementAndGet();
                    System.out.println("Duplicated if condition detected in method: " + method.getName());
                    System.out.println("Duplicated condition: " + ifStmt.getCondition().toString());
                }
            });
        });
        System.out.println("Total duplicated methods detected: " + duplicatesCount.get());
        System.out.println("Total duplicated if conditions detected: " + duplicateIfCount.get());
    }
}

