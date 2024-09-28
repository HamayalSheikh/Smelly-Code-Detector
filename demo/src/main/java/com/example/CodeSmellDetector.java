package com.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class CodeSmellDetector {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java CodeSmellDetector <source_file>");
            return;
        }

        String filePath = args[0];
        parseAndDetectCodeSmells(filePath);
    }

    public static void parseAndDetectCodeSmells(String filePath) throws Exception {
        if (filePath.endsWith(".java")) {
            parseJavaFile(filePath);
        } else if (filePath.endsWith(".cpp")) {
            parseCppFile(filePath);
        } else if (filePath.endsWith(".py")) {
            parsePythonFile(filePath);
        } else {
            System.out.println("Unsupported file type.");
        }
    }

    // Java file parsing using StaticJavaParser
    private static void parseJavaFile(String filePath) throws Exception {
        CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
        System.out.println("Parsed Java file: " + filePath);
        
        // Call code smell detection methods for Java
        CodeSmellAnalyzer.detectJavaCodeSmells(cu);
    }

    // C++ file parsing using basic file reading
    private static void parseCppFile(String filePath) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println("Parsed C++ file: " + filePath);
            
            CodeSmellAnalyzer.detectCppCodeSmells(fileContent);
        } catch (IOException e) {
            System.out.println("Error reading C++ file: " + e.getMessage());
        }
    }

    // Python file parsing using basic file reading
    private static void parsePythonFile(String filePath) {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println("Parsed Python file: " + filePath);
            
            CodeSmellAnalyzer.detectPythonCodeSmells(fileContent);
        } catch (IOException e) {
            System.out.println("Error reading Python file: " + e.getMessage());
        }
    }
}

class CodeSmellAnalyzer {

    // Java code smell detection
    public static void detectJavaCodeSmells(CompilationUnit cu) {
        LongMethodDetector.detectLongMethods(cu, 10);
        GodClassDetector.detectGodClass(cu);
        LargeParameterListDetector.detectLargeParameterList(cu, 5);
        DuplicatedCodeDetector.detectDuplicatedCode(cu);
    }

    // C++ code smell detection
    public static void detectCppCodeSmells(String fileContent) {
        System.out.println("Analyzing C++ file for code smells...");

        // Long method detection based on number of lines
        String[] methods = extractMethodsFromCpp(fileContent);
        for (String method : methods) {
            int length = method.split("\n").length;
            if (length > 30) {
                System.out.println("Long method found in C++ file with " + length + " lines.");
            }
        }

        detectLargeParameterListCpp(fileContent);
        // Add more C++ code-smell logic (e.g., duplicated code detection)
    }

    // Python code smell detection
    public static void detectPythonCodeSmells(String fileContent) {
        System.out.println("Analyzing Python file for code smells...");

        // Long method detection based on indentation and number of lines
        String[] methods = extractMethodsFromPython(fileContent);
        for (String method : methods) {
            int length = method.split("\n").length;
            if (length > 30) {
                System.out.println("Long method found in Python file with " + length + " lines.");
            }
        }

        detectLargeParameterListPython(fileContent);
        // Add more Python code-smell logic (e.g., duplicated code detection)
    }

    // Extract methods from a C++ file using basic string parsing (or use Tree-sitter)
    private static String[] extractMethodsFromCpp(String fileContent) {
        return fileContent.split("void |int |double "); // Naive splitting to simulate method detection
    }

    // Detect large parameter lists in C++ methods (basic string parsing)
    private static void detectLargeParameterListCpp(String fileContent) {
        String[] methods = extractMethodsFromCpp(fileContent);
        for (String method : methods) {
            if (method.contains("(")) {
                String parameterList = method.substring(method.indexOf("(") + 1, method.indexOf(")"));
                String[] parameters = parameterList.split(",");
                if (parameters.length > 5) {
                    System.out.println("Large parameter list detected in C++ method: " + parameters.length + " parameters.");
                }
            }
        }
    }

    // Extract methods from a Python file using basic string parsing (or use Tree-sitter)
    private static String[] extractMethodsFromPython(String fileContent) {
        return fileContent.split("def "); // Naive splitting to simulate method detection
    }

    // Detect large parameter lists in Python functions (basic string parsing)
    private static void detectLargeParameterListPython(String fileContent) {
        String[] methods = extractMethodsFromPython(fileContent);
        for (String method : methods) {
            if (method.contains("(")) {
                String parameterList = method.substring(method.indexOf("(") + 1, method.indexOf(")"));
                String[] parameters = parameterList.split(",");
                if (parameters.length > 5) {
                    System.out.println("Large parameter list detected in Python function: " + parameters.length + " parameters.");
                }
            }
        }
    }
}
