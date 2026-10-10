package app;

import ui.MenuUI;

import java.util.Scanner;

/**
 * Entry point of the Data Structure and Graph Performance Analyzer.
 *
 * CIT300 - Data Structures and Algorithms, Graded Practical Assignment 2
 * SLTC Research University
 *
 * Command line flags:
 *   --sample    load sample data into every structure without asking
 *   --empty     start empty without asking
 *   --no-pause  skip the "Press Enter" pauses (used for automated testing)
 */
public class Main {

    public static void main(String[] args) {
        boolean forceSample = hasFlag(args, "--sample");
        boolean forceEmpty = hasFlag(args, "--empty");
        boolean pauseEnabled = !hasFlag(args, "--no-pause");

        Scanner scanner = new Scanner(System.in);
        MenuUI menu = new MenuUI(scanner, pauseEnabled);

        if (forceSample) {
            menu.loadAllSampleData();
            System.out.println("\n   Sample data loaded into every structure.");
        } else if (!forceEmpty) {
            System.out.print("\n   Load sample data into every structure? (Y/N): ");
            String answer = scanner.hasNextLine() ? scanner.nextLine().trim().toLowerCase() : "n";
            if (answer.equals("y") || answer.equals("yes")) {
                menu.loadAllSampleData();
                System.out.println("   Sample data loaded.");
            } else {
                System.out.println("   Starting with empty structures.");
            }
        }

        try {
            menu.run();
        } catch (java.util.NoSuchElementException e) {
            // Reached when the input stream ends during an automated test run.
            System.out.println("\n   Input stream ended - shutting down.");
        } finally {
            scanner.close();
        }
    }

    private static boolean hasFlag(String[] args, String flag) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }
}
