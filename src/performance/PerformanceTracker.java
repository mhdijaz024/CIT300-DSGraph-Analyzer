package performance;

/**
 * Collects every measured operation so the system can print the performance
 * comparison table required by the assignment.
 *
 * The tracker owns its own growable array rather than using java.util.ArrayList,
 * so no collection class is used anywhere in this project.
 *
 * Responsibility: Member 3 - M.I.M Arshad (23DA2-0634)
 */
public class PerformanceTracker {

    private OperationResult[] results = new OperationResult[8];
    private int count;

    /** Stores one measurement. Grows the backing array when it fills up. */
    public void record(OperationResult result) {
        if (result == null) {
            return;
        }
        if (count == results.length) {
            grow();
        }
        results[count++] = result;
    }

    /** Convenience overload used by the data-structure classes. */
    public void record(String operation, String algorithm, long steps,
                       long nanoSeconds, String outcome, String complexity) {
        record(new OperationResult(operation, algorithm, steps, nanoSeconds, outcome, complexity));
    }

    private void grow() {
        OperationResult[] bigger = new OperationResult[results.length * 2];
        System.arraycopy(results, 0, bigger, 0, count);
        results = bigger;
    }

    /** Every measurement, oldest first. */
    public OperationResult[] all() {
        OperationResult[] out = new OperationResult[count];
        System.arraycopy(results, 0, out, 0, count);
        return out;
    }

    /** Only the measurements whose operation name matches, e.g. "Search". */
    public OperationResult[] filterByOperation(String operation) {
        int matches = 0;
        for (int i = 0; i < count; i++) {
            if (results[i].getOperation().equalsIgnoreCase(operation)) {
                matches++;
            }
        }
        OperationResult[] out = new OperationResult[matches];
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (results[i].getOperation().equalsIgnoreCase(operation)) {
                out[index++] = results[i];
            }
        }
        return out;
    }

    /** The most recent measurement for a given algorithm, or null. */
    public OperationResult latestFor(String algorithm) {
        for (int i = count - 1; i >= 0; i--) {
            if (results[i].getAlgorithm().equalsIgnoreCase(algorithm)) {
                return results[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void clear() {
        results = new OperationResult[8];
        count = 0;
    }
}
