package org.example;

import pemja.core.PythonInterpreter;
import pemja.core.PythonInterpreterConfig;

/**
 * Pemja verification program.
 * This program verifies that pemja can execute Python code normally,
 * especially testing the import of pyflink.
 */
public class PemjaVerification {

    /**
     * Main method to run the verification.
     *
     * @param args command line arguments
     *             Format: [pythonExec] [pythonPath] [pythonHome]
     *             Example: /path/to/python3 /path/to/python/modules /path/to/python/home
     *             
     *             For Python Standalone Builds:
     *             - pythonExec: path to python executable (e.g., /opt/python/bin/python3)
     *             - pythonPath: additional module search paths (optional)
     *             - pythonHome: Python installation directory (e.g., /opt/python)
     *                          This is REQUIRED for Python Standalone Builds
     */
    public static void main(String[] args) {
        System.out.println("Starting Pemja verification...");
        System.out.println("=================================");

        // Parse command line arguments
        String pythonExec = args.length > 0 ? args[0] : null;
        String pythonPath = args.length > 1 ? args[1] : null;
        String pythonHome = args.length > 2 ? args[2] : null;

        // Display configuration
        System.out.println("Configuration:");
        System.out.println("  Python Executable: " + (pythonExec != null ? pythonExec : "default"));
        System.out.println("  Python Path: " + (pythonPath != null ? pythonPath : "default"));
        System.out.println("  Python Home: " + (pythonHome != null ? pythonHome : "default"));
        System.out.println();

        try {

            // Create Python interpreter configuration with parameters
            PythonInterpreterConfig.PythonInterpreterConfigBuilder configBuilder =
                    PythonInterpreterConfig.newBuilder();

            if (pythonExec != null && !pythonExec.isEmpty()) {
                configBuilder.setPythonExec(pythonExec);
            }
            if (pythonPath != null && !pythonPath.isEmpty()) {
                configBuilder.addPythonPaths(pythonPath);
            }

            // IMPORTANT: setPythonHome in config is crucial for Python Standalone Builds
            if (pythonHome != null && !pythonHome.isEmpty()) {
                configBuilder.setPythonHome(pythonHome);
            }

            PythonInterpreterConfig config = configBuilder.build();

            // Create Python interpreter instance
            System.out.println("\nCreating Python interpreter...");
            PythonInterpreter interpreter = new PythonInterpreter(config);

            System.out.println("✓ Pemja interpreter created successfully");

            // Test 1: Basic Python execution
            System.out.println("\nTest 1: Basic Python execution");
            interpreter.exec("print('Hello from Python!')");
            System.out.println("✓ Basic Python execution successful");

            // Test 2: Python version check
            System.out.println("\nTest 2: Python version check");
            interpreter.exec("import sys");
            interpreter.exec("print('Python version:', sys.version)");
            System.out.println("✓ Python version check successful");

            // Test 3: Import pyflink
            System.out.println("\nTest 3: Import pyflink");
            try {
                interpreter.exec("import pyflink");
                interpreter.exec("print('PyFlink version:', pyflink.__version__)");
                System.out.println("✓ PyFlink import successful");
            } catch (Exception e) {
                System.out.println("✗ PyFlink import failed: " + e.getMessage());
                System.out.println("  Note: Make sure pyflink is installed in your Python environment");
                System.out.println("  You can install it using: pip install apache-flink");
            }

            // Test 4: Simple Python calculation
            System.out.println("\nTest 4: Simple Python calculation");
            interpreter.exec("result = 10 + 20");
            Object result = interpreter.get("result");
            System.out.println("  Python calculation result: " + result);
            System.out.println("✓ Python calculation successful");

            // Close the interpreter
            interpreter.close();
            System.out.println("\n=================================");
            System.out.println("Pemja verification completed!");

        } catch (Exception e) {
            System.err.println("\n✗ Pemja verification failed!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
