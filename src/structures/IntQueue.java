package structures;

/**
 * Queue component: First In First Out, with separate front and rear pointers
 * so that enqueue and dequeue are both O(1) rather than O(n).
 *
 * Empty-queue conditions are reported rather than left to crash.
 *
 * Responsibility: Member 2 - Z. Isham (23DA2-0677)
 */
public class IntQueue {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node front;
    private Node rear;
    private int size;
    private long lastStepCount;

    /** Adds a value at the rear. O(1). */
    public void enqueue(int value) {
        lastStepCount = 1;
        Node fresh = new Node(value);
        if (rear == null) {
            front = fresh;
            rear = fresh;
        } else {
            rear.next = fresh;
            rear = fresh;
        }
        size++;
    }

    /**
     * Removes and returns the value at the front. O(1).
     * Throws IllegalStateException when the queue is empty.
     */
    public int dequeue() {
        lastStepCount = 1;
        if (isEmpty()) {
            throw new IllegalStateException("Queue underflow - the queue is empty");
        }
        int value = front.value;
        front = front.next;
        if (front == null) {
            rear = null;            // queue drained, reset the rear pointer too
        }
        size--;
        return value;
    }

    /** Reads the front value without removing it. O(1). */
    public int peek() {
        lastStepCount = 1;
        if (isEmpty()) {
            throw new IllegalStateException("The queue is empty - there is nothing at the front");
        }
        return front.value;
    }

    /** Linear search from the front. O(n). Returns the position, or -1. */
    public int search(int value) {
        lastStepCount = 0;
        Node cursor = front;
        int position = 0;
        while (cursor != null) {
            lastStepCount++;
            if (cursor.value == value) {
                return position;
            }
            cursor = cursor.next;
            position++;
        }
        return -1;
    }

    /** Front-to-rear snapshot used by the display option. */
    public int[] toArray() {
        int[] out = new int[size];
        Node cursor = front;
        int i = 0;
        while (cursor != null) {
            out[i++] = cursor.value;
            cursor = cursor.next;
        }
        return out;
    }

    public String display() {
        if (isEmpty()) {
            return "   (the queue is empty)";
        }
        StringBuilder sb = new StringBuilder("   FRONT -> ");
        Node cursor = front;
        while (cursor != null) {
            sb.append(cursor.value);
            cursor = cursor.next;
            if (cursor != null) {
                sb.append(" -> ");
            }
        }
        sb.append(" <- REAR\n   Size: ").append(size);
        return sb.toString();
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public long getLastStepCount() {
        return lastStepCount;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
