package com.example;

import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;

// A god class often has too many dependencies.

public class GodClassDetector {

    // Threshold for detecting God Class (too many methods or too many fields)
    private static final int METHOD_THRESHOLD = 7;
    private static final int FIELD_THRESHOLD = 4;
    private static final int COUPLING_THRESHOLD = 5;

    public static void detectGodClass(CompilationUnit cu) {
        boolean godClassDetected = false; // Track if any God Class is detected
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
            int methodCount = cls.getMethods().size();
            int fieldCount = cls.getFields().size();

            Set<String> classDependencies = detectClassCoupling(cls);

            boolean methodThresholdExceeded = methodCount > METHOD_THRESHOLD;
            boolean fieldThresholdExceeded = fieldCount > FIELD_THRESHOLD;
            boolean couplingThresholdExceeded = classDependencies.size() > COUPLING_THRESHOLD;


            if (methodThresholdExceeded || fieldThresholdExceeded || couplingThresholdExceeded) {
                godClassDetected = true; // Mark that a God Class has been detected

                System.out.println("God Class detected: " + cls.getName());

                // Report specific thresholds that were exceeded
                if (methodThresholdExceeded) {
                    System.out.println("Number of methods exceeds threshold: " + methodCount + " methods");
                } else {
                    System.out.println("Number of methods is within acceptable range: " + methodCount + " methods");
                }

                if (fieldThresholdExceeded) {
                    System.out.println("Number of fields exceeds threshold: " + fieldCount + " fields");
                } else {
                    System.out.println("Number of fields is within acceptable range: " + fieldCount + " fields");
                }

                if (couplingThresholdExceeded) {
                    System.out.println("Coupling exceeds threshold: " + classDependencies.size() + " dependencies");
                    System.out.println("Classes interacted with: " + classDependencies);
                } else {
                    System.out.println("Coupling is within acceptable range: " + classDependencies.size() + " dependencies");
                }
                System.out.println(); // Blank line for readability
            }
        });

        // If no God Class is detected, print this message
        if (!godClassDetected) {
            System.out.println("No God Class detected in the file.");
        }
            // If the class has too many methods or fields, it's a potential God Class
            // if (methodCount > METHOD_THRESHOLD || fieldCount > FIELD_THRESHOLD || classDependencies.size() > COUPLING_THRESHOLD) {
            //     System.out.println("God Class detected: " + cls.getName());
            //     System.out.println("Number of methods: " + methodCount);
            //     System.out.println("Number of fields: " + fieldCount);
            //     System.out.println("Number of Dependencies (Coupling): " + classDependencies.size());
            //     System.out.println("Classes interacted with: " + classDependencies);
            // }
        
    }

      private static Set<String> detectClassCoupling(ClassOrInterfaceDeclaration cls) {
        Set<String> classDependencies = new HashSet<>();

        // Find all field declarations and add their types to the dependencies
        cls.findAll(FieldDeclaration.class).forEach(field -> {
            field.getVariables().forEach(var -> {
                classDependencies.add(var.getType().asString());
            });
        });

        // Find all object creations (new ClassName()) and add their types to the dependencies
        cls.findAll(ObjectCreationExpr.class).forEach(expr -> {
            classDependencies.add(expr.getType().asString());
        });

        // Find all method calls and add the class/object being called
        cls.findAll(MethodCallExpr.class).forEach(call -> {
            call.getScope().ifPresent(scope -> {
                String className = scope.toString(); // Get the class/object being called
                classDependencies.add(className);
            });
        });

        return classDependencies;
    }
}


// public class GodClassDetector {
//     public static void detectGodClasses(CompilationUnit cu, int methodThreshold, int fieldThreshold) {
//         cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
//             int methodCount = cls.getMethods().size();
//             int fieldCount = cls.getFields().size();
//             if (methodCount > methodThreshold || fieldCount > fieldThreshold) {
//                 System.out.println("God class detected: " + cls.getName());
//             }
//         });
//     }
// }

// public class GodClassDetector {
//     public static void detectGodClasses(CompilationUnit cu, int methodThreshold, int fieldThreshold) {
//         cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
//             int methodCount = cls.getMethods().size();
//             int fieldCount = cls.getFields().size();
//             if (methodCount > methodThreshold || fieldCount > fieldThreshold) {
//                 System.out.println("God class detected: " + cls.getName() + " with " + methodCount + " methods and " + fieldCount + " fields.");
//             }
//         });
//     }
// }
