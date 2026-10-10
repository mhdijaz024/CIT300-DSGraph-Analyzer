package structures;

/**
 * Linked list component: a singly linked list of integers with insert at head,
 * insert at tail, insert at a position, delete by value, delete by position,
 * search and display. Written from scratch - no java.util class is used.
 *
 * Responsibility: Member 3 - M.I.M Arshad (23DA2-0634)
 */
public class SinglyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private int size;
    private long lastStepCount;

    // ----------------------------------------------------------------- insert

    /** Inserts at the front. O(1) - no traversal needed. */
    public void insertAtHead(int value) {
        lastStepCount = 1;
        Node fresh = new Node(value);
        fresh.next = head;
        head = fresh;
        size++;
    }

    /** Inserts at the end. O(n) because the tail must be walked to. */
    public void insertAtTail(int value) {
        lastStepCount = 0;
        Node fresh = new Node(value);
        if (head == null) {
            head = fresh;
            lastStepCount = 1;
        } else {
            Node cursor = head;
            while (cursor.next != null) {
                cursor = cursor.next;
                lastStepCount++;
            }
            cursor.next = fresh;
            lastStepCount++;
        }
        size++;
    }

    /**
     * Inserts at a zero-based position. O(n).
     * Returns false when the position is outside 0..size.
     */
    public boolean insertAt(int position, int value) {
        lastStepCount = 0;
        if (position < 0 || position > size) {
            return false;
        }
        if (position == 0) {
            insertAtHead(value);
            return true;
        }
        Node cursor = head;
        for (int i = 0; i < position - 1; i++) {
            cursor = cursor.next;
            lastStepCount++;
        }
        Node fresh = new Node(value);
        fresh.next = cursor.next;
        cursor.next = fresh;
        lastStepCount++;
        size++;
        return true;
    }

    // ----------------------------------------------------------------- delete

    /**
     * Deletes the first node holding the value. O(n).
     * Returns the position it was removed from, or -1 when not found.
     */
    public int deleteValue(int value) {
        lastStepCount = 0;
        if (head == null) {
            return -1;
        }
        lastStepCount++;
        if (head.value == value) {
            head = head.next;
            size--;
            return 0;
        }
        Node previous = head;
        int position = 1;
        while (previous.next != null) {
            lastStepCount++;
            if (previous.next.value == value) {
                previous.next = previous.next.next;   // bypass the node
                size--;
                return position;
            }
            previous = previous.next;
            position++;
        }
        return -1;
    }

    /** Deletes by position. Returns the removed value, or throws when invalid. */
    public int deleteAt(int position) {
        lastStepCount = 0;
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Position " + position + " is outside 0.." + (size - 1));
        }
        if (position == 0) {
            int removed = head.value;
            head = head.next;
            size--;
            lastStepCount = 1;
            return removed;
        }
        Node previous = head;
        for (int i = 0; i < position - 1; i++) {
            previous = previous.next;
            lastStepCount++;
        }
        int removed = previous.next.value;
        previous.next = previous.next.next;
        lastStepCount++;
        size--;
        return removed;
    }

    // ----------------------------------------------------------------- search

    /** Linear search from the head. O(n). Returns the position, or -1. */
    public int search(int value) {
        lastStepCount = 0;
        Node cursor = head;
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

    /** Reverses the list in place by re-pointing every link. O(n). */
    public void reverse() {
        lastStepCount = 0;
        Node previous = null;
        Node cursor = head;
        while (cursor != null) {
            Node next = cursor.next;
            cursor.next = previous;
            previous = cursor;
            cursor = next;
            lastStepCount++;
        }
        head = previous;
    }

    // ---------------------------------------------------------------- display

    public int[] toArray() {
        int[] out = new int[size];
        Node cursor = head;
        int i = 0;
        while (cursor != null) {
            out[i++] = cursor.value;
            cursor = cursor.next;
        }
        return out;
    }

    public String display() {
        if (head == null) {
            return "   (the linked list is empty)";
        }
        StringBuilder sb = new StringBuilder("   HEAD -> ");
        Node cursor = head;
        while (cursor != null) {
            sb.append("[").append(cursor.value).append("]");
            cursor = cursor.next;
            sb.append(" -> ");
        }
        sb.append("null\n   Size: ").append(size);
        return sb.toString();
    }

    public long getLastStepCount() {
        return lastStepCount;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        size = 0;
    }
}
