package ui;

import java.util.Scanner;

/**
 * Every console read passes through here, so validation is applied
 * consistently and the menu code stays readable.
 *
 * Responsibility: all four members (shared utility).
 */
public class InputValidator {

    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Reads a non-empty line, re-prompting until one is given. */
    public String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("   ! This cannot be left blank. Please try again.");
        }
    }

    /** Reads any whole number, rejecting text and decimals. */
    public int readInt(String prompt) {
        while (true) {
            String raw = readNonEmpty(prompt);
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println("   ! '" + raw + "' is not a whole number. Try again.");
            }
        }
    }

    /** Reads a whole number inside an inclusive range. */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("   ! Enter a number between " + min + " and " + max + ".");
        }
    }

    /** Reads a vertex or label name: letters, digits, spaces, hyphens. */
    public String readName(String prompt) {
        while (true) {
            String name = readNonEmpty(prompt);
            if (name.matches("[A-Za-z0-9 _-]{1,20}")) {
                return name;
            }
            System.out.println("   ! Use 1-20 letters, digits, spaces, hyphens or underscores.");
        }
    }

    /** Reads a yes/no answer. */
    public boolean readYesNo(String prompt) {
        while (true) {
            String raw = readNonEmpty(prompt).toLowerCase();
            if (raw.equals("y") || raw.equals("yes")) {
                return true;
            }
            if (raw.equals("n") || raw.equals("no")) {
                return false;
            }
            System.out.println("   ! Please answer Y or N.");
        }
    }

    public void pause() {
        System.out.print("\n   Press Enter to continue...");
        scanner.nextLine();
    }
}
