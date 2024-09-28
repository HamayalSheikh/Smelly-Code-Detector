package com.example;


import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;




public class CodeSmellDetector {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java CodeSmellDetectionTool <source_file>");
            return;
        }

         String filePath = args[0];
        MultiLanguageParser.parseFile(filePath);

        // Example for Java analysis
        if (filePath.endsWith(".java")) {
            CompilationUnit cu = StaticJavaParser.parse(new File(filePath));

            // Detect Long Method
            LongMethodDetector.detectLongMethods(cu, 30);

            // Detect God Class
            GodClassDetector.detectGodClasses(cu, 10, 10);

            // Other smell detections...
        }


    }
}
