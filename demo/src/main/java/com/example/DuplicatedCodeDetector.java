package com.example;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.javaparser.ast.CompilationUnit;

public class DuplicatedCodeDetector {
    public static void detectDuplicatedCode(CompilationUnit cu) {
        Set<String> methodBodies = new HashSet<>();
        AtomicInteger duplicatesCount = new AtomicInteger(0);
        
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            // Normalize the method body (remove whitespaces and comments)
            String body = method.getBody().map(Object::toString).orElse("")
                .replaceAll("\\s+", "") // Remove all whitespace
                .replaceAll("//.*|/\\*.*\\*/", ""); // Remove comments
            
            if (!methodBodies.add(body)) {
                duplicatesCount.incrementAndGet();
                System.out.println("Duplicated Method detected: " + method.getName());
            }
        });
        
        System.out.println("Total duplicated methods detected: " + duplicatesCount.get());
    }
}

// public class DuplicatedCodeDetector {

//     public static void detectDuplicatedCode(CompilationUnit cu) {
//         Set<String> methodBodies = new HashSet<>();
//         cu.findAll(MethodDeclaration.class).forEach(method -> {
//             String body = method.getBody().toString();
//             if (!methodBodies.add(body)) {
//                 System.out.println("Duplicated Method detected: " + method.getName());
//             }
//         });
//     }
// }


// public class DuplicatedCodeDetector {
//     public static void detectDuplicatedCode(CompilationUnit cu) {
//         Set<String> methodBodies = new HashSet<>();
//         cu.findAll(MethodDeclaration.class).forEach(method -> {
//             String body = method.getBody().toString().replaceAll("\\s+", " ").trim(); // Normalize whitespace
//             if (!methodBodies.add(body)) {
//                 System.out.println("Duplicated Method detected: " + method.getName());
//             }
//         });
//     }
// }


