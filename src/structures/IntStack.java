package structures;

/**
 * Stack component: Last In First Out, built on linked nodes so it never runs
 * out of capacity. Push, pop and peek are all O(1).
 *
 * Empty-stack conditions are handled by returning a status rather than
 * crashing, so the menu can report them cleanly.
 *
 * Responsibility: Member 2 - Z. Isham (23DA2-0677)
 */
public class IntStack {

    private static class Node {
        int value;
        Node below;

        Node(int value, Node below) {
            this.value = value;
            this.below = below;
        }
    }

    private Node top;
    private int size;
    private long lastStepCount;

    /** Puts a value on top of the stack. O(1). */
    public void push(int value) {
        lastStepCount = 1;
        top = new Node(value, top);
        size++;
    }

    /**
     * Removes and returns the top value. O(1).
     * Throws IllegalStateException when the stack is empty - the caller is
     * expected to check isEmpty() first, which the menu does.
     */
    public int pop() {
        lastStepCount = 1;
        if (isEmpty()) {
            throw new IllegalStateException("Stack underflow - the stack is empty");
        }
        int value = top.value;
        top = top.below;
        size--;
        return value;
    }

    /** Reads the top value without removing it. O(1). */
    public int peek() {
        lastStepCount = 1;
        if (isEmpty()) {
            throw new IllegalStateException("The stack is empty - there is nothing to peek at");
        }
        return top.value;
    }

    /** Linear search from the top down. O(n). Returns depth from top, or -1. */
    public int search(int value) {
        lastStepCount = 0;
        Node cursor = top;
        int depth = 0;
        while (cursor != null) {
            lastStepCount++;
            if (cursor.value == value) {
                return depth;
            }
            cursor = cursor.below;
            depth++;
        }
        return -1;
    }

    /** Top-to-bottom snapshot used by the display option. */
    public int[] toArray() {
        int[] out = new int[size];
        Node cursor = top;
        int i = 0;
        while (cursor != null) {
            out[i++] = cursor.value;
            cursor = cursor.below;
        }
        return out;
    }

    public String display() {
        if (isEmpty()) {
            return "   (the stack is empty)";
        }
        StringBuilder sb = new StringBuilder();
        Node cursor = top;
        boolean first = true;
        while (cursor != null) {
            sb.append(String.format("   %-8s %d%n", first ? "TOP ->" : "", cursor.value));
            first = false;
            cursor = cursor.below;
        }
        sb.append(String.format("   %-8s%n", "BOTTOM"));
        sb.append("   Size: ").append(size);
        return sb.toString();
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public long getLastStepCount() {
        return lastStepCount;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
