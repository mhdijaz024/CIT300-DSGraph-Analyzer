package algorithms;

/**
 * Searching component: linear search and binary search, each counting the
 * comparisons it makes so the two can be compared honestly on the same data.
 *
 * Both methods return a SearchOutcome rather than a bare index, because the
 * step count is as important to this assignment as the result itself.
 *
 * Responsibility: Member 1 - M.H.M Ijas (23DA2-0675)
 */
public class SearchAlgorithms {

    /** The result of one search: where it was found and what it cost. */
    public static class SearchOutcome {
        private final int index;
        private final long steps;
        private final long nanoSeconds;
        private final String algorithm;
        private final String complexity;

        public SearchOutcome(int index, long steps, long nanoSeconds,
                             String algorithm, String complexity) {
            this.index = index;
            this.steps = steps;
            this.nanoSeconds = nanoSeconds;
            this.algorithm = algorithm;
            this.complexity = complexity;
        }

        public int getIndex() {
            return index;
        }

        public long getSteps() {
            return steps;
        }

        public long getNanoSeconds() {
            return nanoSeconds;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public String getComplexity() {
            return complexity;
        }

        public boolean isFound() {
            return index >= 0;
        }

        public String describe() {
            return isFound() ? ("Found at index " + index) : "Not found";
        }
    }

    /**
     * Linear search: checks each element in turn until it finds the target.
     *
     * Works on unsorted data, which is its advantage, but it may have to look
     * at every element, so it is O(n) in the worst case.
     */
    public static SearchOutcome linearSearch(int[] values, int target) {
        long steps = 0;
        long start = System.nanoTime();
        int foundAt = -1;

        for (int i = 0; i < values.length; i++) {
            steps++;                       // one comparison
            if (values[i] == target) {
                foundAt = i;
                break;
            }
        }

        long elapsed = System.nanoTime() - start;
        return new SearchOutcome(foundAt, steps, elapsed, "Linear Search", "O(n)");
    }

    /**
     * Binary search: repeatedly halves the search range.
     *
     * REQUIRES SORTED DATA. Each comparison discards half of what is left,
     * so it is O(log n) - 1000 elements need about 10 comparisons, not 1000.
     */
    public static SearchOutcome binarySearch(int[] sortedValues, int target) {
        long steps = 0;
        long start = System.nanoTime();
        int foundAt = -1;

        int low = 0;
        int high = sortedValues.length - 1;

        while (low <= high) {
            steps++;                       // one comparison per halving
            int mid = low + (high - low) / 2;   // avoids integer overflow

            if (sortedValues[mid] == target) {
                foundAt = mid;
                break;
            }
            if (sortedValues[mid] < target) {
                low = mid + 1;             // discard the lower half
            } else {
                high = mid - 1;            // discard the upper half
            }
        }

        long elapsed = System.nanoTime() - start;
        return new SearchOutcome(foundAt, steps, elapsed, "Binary Search", "O(log n)");
    }

    /**
     * Checks a binary search is safe to run. Binary search on unsorted data
     * silently returns wrong answers, so the menu uses this before offering it.
     */
    public static boolean isSorted(int[] values) {
        for (int i = 1; i < values.length; i++) {
            if (values[i - 1] > values[i]) {
                return false;
            }
        }
        return true;
    }

    /** Ascending bubble sort on a copy, returning the sorted copy. */
    public static int[] sortedCopy(int[] values) {
        int[] copy = new int[values.length];
        System.arraycopy(values, 0, copy, 0, values.length);
        for (int pass = 0; pass < copy.length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < copy.length - 1 - pass; i++) {
                if (copy[i] > copy[i + 1]) {
                    int temp = copy[i];
                    copy[i] = copy[i + 1];
                    copy[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return copy;
    }
}
