package performance;

/**
 * One recorded measurement: which operation ran, on which data structure or
 * algorithm, how many basic steps it took, how long it took, and what it found.
 *
 * Responsibility: Member 3 - M.I.M Arshad (23DA2-0634)
 */
public class OperationResult {

    private final String operation;      // e.g. "Search"
    private final String algorithm;      // e.g. "Binary Search"
    private final long steps;            // basic operations counted
    private final long nanoSeconds;      // wall-clock execution time
    private final String outcome;        // e.g. "Found at index 7"
    private final String complexity;     // e.g. "O(log n)"

    public OperationResult(String operation, String algorithm, long steps,
                           long nanoSeconds, String outcome, String complexity) {
        this.operation = operation;
        this.algorithm = algorithm;
        this.steps = steps;
        this.nanoSeconds = nanoSeconds;
        this.outcome = outcome;
        this.complexity = complexity;
    }

    public String getOperation() {
        return operation;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public long getSteps() {
        return steps;
    }

    public long getNanoSeconds() {
        return nanoSeconds;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getComplexity() {
        return complexity;
    }

    /** Milliseconds, for a more readable display of the slower operations. */
    public double getMilliSeconds() {
        return nanoSeconds / 1_000_000.0;
    }

    /** One row of the performance comparison table. */
    public String toRow() {
        return String.format("| %-16s | %-18s | %8d | %12d | %-10s | %-24s |",
                operation, algorithm, steps, nanoSeconds, complexity, truncate(outcome, 24));
    }

    private String truncate(String text, int max) {
        if (text == null) {
            return "";
        }
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    @Override
    public String toString() {
        return String.format("%s / %s : %d steps, %d ns, %s",
                operation, algorithm, steps, nanoSeconds, outcome);
    }
}
