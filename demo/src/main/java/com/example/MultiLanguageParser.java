package com.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class MultiLanguageParser {
    public static void parseFile(String filePath) throws Exception {
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
 public static void parseJavaFile(String filePath) throws Exception {
    CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
    System.out.println("Parsed Java file: " + filePath);

    // Call code smell detection methods for Java
    LongMethodDetector.detectLongMethods(cu, 30);
    GodClassDetector.detectGodClasses(cu, 10, 10);
    LargeParameterListDetector.detectLargeParameterList(cu, 5);
    DuplicatedCodeDetector.detectDuplicatedCode(cu);
}

// C++ file parsing using Tree-sitter
public static void parseCppFile(String filePath) {
    try {
        // This is a placeholder for Tree-sitter integration
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println("Parsed C++ file: " + filePath);
        
        detectCodeSmellsCpp(fileContent);
    } catch (IOException e) {
        System.out.println("Error reading C++ file: " + e.getMessage());
    }
}

// Python file parsing using Tree-sitter
public static void parsePythonFile(String filePath) {
    try {
        // This is a placeholder for Tree-sitter integration
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println("Parsed Python file: " + filePath);
       
        detectCodeSmellsPython(fileContent);
    } catch (IOException e) {
        System.out.println("Error reading Python file: " + e.getMessage());
    }
}

// Placeholder method for detecting code smells in C++
public static void detectCodeSmellsCpp(String fileContent) {
       // Implement C++ specific code smell detection logic
       System.out.println("Analyzing C++ file for code smells...");

       // Example: Long method detection based on number of lines
       String[] methods = extractMethodsFromCpp(fileContent);
       for (String method : methods) {
           int length = method.split("\n").length;
           if (length > 30) {
               System.out.println("Long method found in C++ file with " + length + " lines.");
           }
       }

       // Example: Detect large parameter lists in C++ methods
       detectLargeParameterListCpp(fileContent);

       // Add more C++ code-smell logic (e.g., duplicated code detection)
}

// Placeholder method for detecting code smells in Python
public static void detectCodeSmellsPython(String fileContent) {
   // Implement Python specific code smell detection logic
   System.out.println("Analyzing Python file for code smells...");

   // Example: Long method detection based on indentation and number of lines
   String[] methods = extractMethodsFromPython(fileContent);
   for (String method : methods) {
       int length = method.split("\n").length;
       if (length > 30) {
           System.out.println("Long method found in Python file with " + length + " lines.");
       }
   }

   // Example: Detect large parameter lists in Python functions
   detectLargeParameterListPython(fileContent);

   // Add more Python code-smell logic (e.g., duplicated code detection)
}

// Extract methods from a C++ file using basic string parsing (or use Tree-sitter)
public static String[] extractMethodsFromCpp(String fileContent) {
    // This is a placeholder. A proper implementation would use Tree-sitter or regex to extract methods
    return fileContent.split("void |int |double "); // Naive splitting to simulate method detection
}

// Detect large parameter lists in C++ methods (basic string parsing)
public static void detectLargeParameterListCpp(String fileContent) {
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
public static String[] extractMethodsFromPython(String fileContent) {
    // Placeholder for extracting methods (e.g., splitting on "def ")
    return fileContent.split("def "); // Naive splitting to simulate method detection
}

// Detect large parameter lists in Python functions (basic string parsing)
public static void detectLargeParameterListPython(String fileContent) {
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
