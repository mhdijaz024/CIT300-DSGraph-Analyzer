package structures;

/**
 * Array component: a growable integer array built on a plain Java array.
 * Every operation counts the basic steps it performs so the performance
 * comparison can report real numbers rather than estimates.
 *
 * Responsibility: Member 1 - M.H.M Ijas (23DA2-0675)
 */
public class DynamicArray {

    private static final int DEFAULT_CAPACITY = 10;

    private int[] data;
    private int size;
    private long lastStepCount;     // steps taken by the most recent operation

    public DynamicArray() {
        this.data = new int[DEFAULT_CAPACITY];
    }

    public DynamicArray(int capacity) {
        this.data = new int[Math.max(capacity, 1)];
    }

    // ----------------------------------------------------------------- insert

    /** Appends a value at the end. O(1) amortised. */
    public boolean insert(int value) {
        lastStepCount = 0;
        if (size == data.length) {
            grow();
        }
        data[size++] = value;
        lastStepCount++;
        return true;
    }

    /**
     * Inserts a value at a given index, shifting the later elements right.
     * O(n) because of the shift. Returns false when the index is invalid.
     */
    public boolean insertAt(int index, int value) {
        lastStepCount = 0;
        if (index < 0 || index > size) {
            return false;
        }
        if (size == data.length) {
            grow();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            lastStepCount++;
        }
        data[index] = value;
        lastStepCount++;
        size++;
        return true;
    }

    private void grow() {
        int[] bigger = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            bigger[i] = data[i];
            lastStepCount++;
        }
        data = bigger;
    }

    // ----------------------------------------------------------------- delete

    /**
     * Deletes the first occurrence of a value, shifting the later elements
     * left. O(n). Returns the index it was removed from, or -1 when absent.
     */
    public int deleteValue(int value) {
        lastStepCount = 0;
        for (int i = 0; i < size; i++) {
            lastStepCount++;
            if (data[i] == value) {
                shiftLeftFrom(i);
                return i;
            }
        }
        return -1;
    }

    /** Deletes by index. Returns the removed value, or throws when invalid. */
    public int deleteAt(int index) {
        lastStepCount = 0;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is outside 0.." + (size - 1));
        }
        int removed = data[index];
        shiftLeftFrom(index);
        return removed;
    }

    private void shiftLeftFrom(int index) {
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            lastStepCount++;
        }
        size--;
    }

    // ----------------------------------------------------------------- search

    /** Sequential search through the array. O(n). Returns the index or -1. */
    public int search(int value) {
        lastStepCount = 0;
        for (int i = 0; i < size; i++) {
            lastStepCount++;
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is outside 0.." + (size - 1));
        }
        return data[index];
    }

    public boolean set(int index, int value) {
        if (index < 0 || index >= size) {
            return false;
        }
        data[index] = value;
        return true;
    }

    // ------------------------------------------------------------------- sort

    /**
     * Ascending bubble sort, needed before a binary search can run.
     * O(n^2), with an early exit when a pass makes no swap.
     */
    public long sort() {
        lastStepCount = 0;
        for (int pass = 0; pass < size - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < size - 1 - pass; i++) {
                lastStepCount++;
                if (data[i] > data[i + 1]) {
                    int temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;          // already in order, no need to keep going
            }
        }
        return lastStepCount;
    }

    /** True when the values are already in ascending order. */
    public boolean isSorted() {
        for (int i = 1; i < size; i++) {
            if (data[i - 1] > data[i]) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------- display

    /** A copy of the live values, so callers cannot modify the internal array. */
    public int[] toArray() {
        int[] out = new int[size];
        System.arraycopy(data, 0, out, 0, size);
        return out;
    }

    public String display() {
        if (size == 0) {
            return "   (the array is empty)";
        }
        StringBuilder sb = new StringBuilder("   Index : ");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%6d", i));
        }
        sb.append("\n   Value : ");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%6d", data[i]));
        }
        sb.append(String.format("%n   Size: %d   Capacity: %d   Sorted: %s",
                size, data.length, isSorted() ? "yes" : "no"));
        return sb.toString();
    }

    public long getLastStepCount() {
        return lastStepCount;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        data = new int[DEFAULT_CAPACITY];
        size = 0;
    }
}
