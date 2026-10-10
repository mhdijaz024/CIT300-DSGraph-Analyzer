package ui;

import algorithms.SearchAlgorithms;
import algorithms.SearchAlgorithms.SearchOutcome;
import performance.OperationResult;
import performance.PerformanceTracker;
import structures.DynamicArray;
import structures.Graph;
import structures.IntQueue;
import structures.IntStack;
import structures.SinglyLinkedList;

import java.util.Scanner;

/**
 * The integrated console interface: a main menu with a submenu for every
 * component, exactly as the assignment specifies.
 *
 * Every measurable operation is recorded in the PerformanceTracker, which
 * feeds options 7 and 8.
 *
 * Responsibility: all four members (integration), led by M.H.M Ijas.
 */
public class MenuUI {

    private static final String BAR =
            "=================================================================";
    private static final String TABLE_LINE =
            "+------------------+--------------------+----------+--------------+------------+--------------------------+";
    private static final String TABLE_HEAD =
            "| Operation        | Algorithm          |    Steps |    Time (ns) | Complexity | Result                   |";

    private final DynamicArray array = new DynamicArray();
    private final IntStack stack = new IntStack();
    private final IntQueue queue = new IntQueue();
    private final SinglyLinkedList linkedList = new SinglyLinkedList();
    private final Graph graph = new Graph();
    private final PerformanceTracker tracker = new PerformanceTracker();

    private final InputValidator input;
    private final boolean pauseEnabled;

    public MenuUI(Scanner scanner, boolean pauseEnabled) {
        this.input = new InputValidator(scanner);
        this.pauseEnabled = pauseEnabled;
    }

    // ------------------------------------------------------------- main loop

    public void run() {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = input.readInt("   Enter your choice (1-9): ", 1, 9);
            System.out.println();
            switch (choice) {
                case 1: arrayMenu();        break;
                case 2: stackMenu();        break;
                case 3: queueMenu();        break;
                case 4: linkedListMenu();   break;
                case 5: searchingMenu();    break;
                case 6: graphMenu();        break;
                case 7: performanceTable(); break;
                case 8: displayAllResults();break;
                case 9: running = confirmExit(); break;
                default: System.out.println("   Invalid option.");
            }
        }
    }

    private void printBanner() {
        System.out.println();
        System.out.println(BAR);
        System.out.println("        DATA STRUCTURE & GRAPH PERFORMANCE ANALYZER");
        System.out.println("     CIT300 - Data Structures and Algorithms | Assignment 2");
        System.out.println("                 SLTC Research University");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("   Group Members:");
        System.out.println("     M.H.M Ijas   - 23DA2-0675 (Leader) : Array + Searching");
        System.out.println("     Z. Isham     - 23DA2-0677          : Stack + Queue");
        System.out.println("     M.I.M Arshad - 23DA2-0634          : Linked List + Performance");
        System.out.println("     M.N.M Nafeel - 23DA2-0678          : Graph + BFS/DFS");
        System.out.println(BAR);
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("   " + BAR);
        System.out.println("            DATA STRUCTURE & GRAPH ANALYZER - MAIN MENU");
        System.out.println("   " + BAR);
        System.out.println("     1. Array Operations");
        System.out.println("     2. Stack Operations");
        System.out.println("     3. Queue Operations");
        System.out.println("     4. Linked List Operations");
        System.out.println("     5. Searching Operations");
        System.out.println("     6. Graph Operations");
        System.out.println("     7. Performance Comparison");
        System.out.println("     8. Display All Results");
        System.out.println("     9. Exit");
        System.out.println("   " + BAR);
        System.out.printf ("   Array:%d  Stack:%d  Queue:%d  List:%d  Vertices:%d  Edges:%d  Results:%d%n",
                array.size(), stack.size(), queue.size(), linkedList.size(),
                graph.getVertexCount(), graph.getEdgeCount(), tracker.size());
    }

    private void pause() {
        if (pauseEnabled) {
            input.pause();
        }
    }

    private void header(String title, String owner) {
        System.out.println("   --------------- " + title + " ---------------");
        System.out.println("   [" + owner + "]");
        System.out.println();
    }

    // ----------------------------------------------------------- 1. ARRAY

    private void arrayMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("ARRAY OPERATIONS", "Member 1 - M.H.M Ijas");
            System.out.println("     1. Insert at end");
            System.out.println("     2. Insert at index");
            System.out.println("     3. Delete by value");
            System.out.println("     4. Delete at index");
            System.out.println("     5. Search (linear scan)");
            System.out.println("     6. Sort (ascending)");
            System.out.println("     7. Display array");
            System.out.println("     8. Load sample data");
            System.out.println("     9. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-9): ", 1, 9);
            System.out.println();

            switch (choice) {
                case 1: {
                    int value = input.readInt("   Value to insert: ");
                    long start = System.nanoTime();
                    array.insert(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Insert", "Array (end)", array.getLastStepCount(),
                            elapsed, "Inserted " + value, "O(1)");
                    System.out.println("   [OK] " + value + " inserted. Size is now " + array.size() + ".");
                    break;
                }
                case 2: {
                    if (array.isEmpty()) {
                        System.out.println("   The array is empty, so index 0 is the only valid position.");
                    }
                    int index = input.readInt("   Index (0.." + array.size() + "): ", 0, array.size());
                    int value = input.readInt("   Value to insert: ");
                    long start = System.nanoTime();
                    boolean ok = array.insertAt(index, value);
                    long elapsed = System.nanoTime() - start;
                    if (ok) {
                        tracker.record("Insert", "Array (at index)", array.getLastStepCount(),
                                elapsed, "Inserted at " + index, "O(n)");
                        System.out.println("   [OK] " + value + " inserted at index " + index
                                + " after shifting " + array.getLastStepCount() + " element(s).");
                    } else {
                        System.out.println("   ! Invalid index.");
                    }
                    break;
                }
                case 3: {
                    if (emptyArrayGuard()) break;
                    int value = input.readInt("   Value to delete: ");
                    long start = System.nanoTime();
                    int index = array.deleteValue(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Delete", "Array (by value)", array.getLastStepCount(), elapsed,
                            index >= 0 ? ("Removed from " + index) : "Value not found", "O(n)");
                    if (index >= 0) {
                        System.out.println("   [OK] " + value + " removed from index " + index + ".");
                    } else {
                        System.out.println("   ! " + value + " is not in the array.");
                    }
                    break;
                }
                case 4: {
                    if (emptyArrayGuard()) break;
                    int index = input.readInt("   Index to delete (0.." + (array.size() - 1) + "): ",
                            0, array.size() - 1);
                    long start = System.nanoTime();
                    int removed = array.deleteAt(index);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Delete", "Array (at index)", array.getLastStepCount(),
                            elapsed, "Removed " + removed, "O(n)");
                    System.out.println("   [OK] " + removed + " removed from index " + index + ".");
                    break;
                }
                case 5: {
                    if (emptyArrayGuard()) break;
                    int value = input.readInt("   Value to search for: ");
                    long start = System.nanoTime();
                    int index = array.search(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Search", "Array scan", array.getLastStepCount(), elapsed,
                            index >= 0 ? ("Found at index " + index) : "Not found", "O(n)");
                    System.out.println(index >= 0
                            ? "   [FOUND] " + value + " is at index " + index
                              + " after " + array.getLastStepCount() + " comparison(s)."
                            : "   [NOT FOUND] " + value + " is not in the array ("
                              + array.getLastStepCount() + " comparisons).");
                    break;
                }
                case 6: {
                    if (emptyArrayGuard()) break;
                    long start = System.nanoTime();
                    long steps = array.sort();
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Sort", "Bubble Sort", steps, elapsed,
                            "Sorted " + array.size() + " values", "O(n^2)");
                    System.out.println("   [OK] Sorted in " + steps + " comparison(s).");
                    System.out.println(array.display());
                    break;
                }
                case 7:
                    System.out.println(array.display());
                    break;
                case 8:
                    loadSampleArray();
                    System.out.println("   [OK] Sample data loaded.");
                    System.out.println(array.display());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    private boolean emptyArrayGuard() {
        if (array.isEmpty()) {
            System.out.println("   ! The array is empty. Insert some values first (option 1 or 8).");
            pause();
            return true;
        }
        return false;
    }

    private void loadSampleArray() {
        array.clear();
        int[] sample = {45, 12, 78, 23, 90, 7, 56, 34, 67, 19, 83, 2, 50, 71, 38};
        for (int value : sample) {
            array.insert(value);
        }
    }

    // ----------------------------------------------------------- 2. STACK

    private void stackMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("STACK OPERATIONS (LIFO)", "Member 2 - Z. Isham");
            System.out.println("     1. Push");
            System.out.println("     2. Pop");
            System.out.println("     3. Peek");
            System.out.println("     4. Search");
            System.out.println("     5. Display stack");
            System.out.println("     6. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-6): ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1: {
                    int value = input.readInt("   Value to push: ");
                    long start = System.nanoTime();
                    stack.push(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Push", "Stack", stack.getLastStepCount(), elapsed,
                            "Pushed " + value, "O(1)");
                    System.out.println("   [OK] " + value + " pushed. Stack size is now " + stack.size() + ".");
                    break;
                }
                case 2: {
                    if (stack.isEmpty()) {
                        System.out.println("   ! STACK UNDERFLOW - the stack is empty, nothing to pop.");
                        break;
                    }
                    long start = System.nanoTime();
                    int value = stack.pop();
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Pop", "Stack", stack.getLastStepCount(), elapsed,
                            "Popped " + value, "O(1)");
                    System.out.println("   [OK] Popped " + value + ". " + stack.size() + " item(s) left.");
                    break;
                }
                case 3: {
                    if (stack.isEmpty()) {
                        System.out.println("   ! The stack is empty, there is nothing on top.");
                        break;
                    }
                    System.out.println("   Top of the stack: " + stack.peek() + " (not removed)");
                    break;
                }
                case 4: {
                    if (stack.isEmpty()) {
                        System.out.println("   ! The stack is empty.");
                        break;
                    }
                    int value = input.readInt("   Value to search for: ");
                    long start = System.nanoTime();
                    int depth = stack.search(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Search", "Stack scan", stack.getLastStepCount(), elapsed,
                            depth >= 0 ? ("Depth " + depth + " from top") : "Not found", "O(n)");
                    System.out.println(depth >= 0
                            ? "   [FOUND] " + value + " is " + depth + " place(s) below the top."
                            : "   [NOT FOUND] " + value + " is not on the stack.");
                    break;
                }
                case 5:
                    System.out.println(stack.display());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    // ----------------------------------------------------------- 3. QUEUE

    private void queueMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("QUEUE OPERATIONS (FIFO)", "Member 2 - Z. Isham");
            System.out.println("     1. Enqueue");
            System.out.println("     2. Dequeue");
            System.out.println("     3. Peek / Front");
            System.out.println("     4. Search");
            System.out.println("     5. Display queue");
            System.out.println("     6. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-6): ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1: {
                    int value = input.readInt("   Value to enqueue: ");
                    long start = System.nanoTime();
                    queue.enqueue(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Enqueue", "Queue", queue.getLastStepCount(), elapsed,
                            "Enqueued " + value, "O(1)");
                    System.out.println("   [OK] " + value + " joined the rear. Queue size " + queue.size() + ".");
                    break;
                }
                case 2: {
                    if (queue.isEmpty()) {
                        System.out.println("   ! QUEUE UNDERFLOW - the queue is empty, nothing to dequeue.");
                        break;
                    }
                    long start = System.nanoTime();
                    int value = queue.dequeue();
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Dequeue", "Queue", queue.getLastStepCount(), elapsed,
                            "Dequeued " + value, "O(1)");
                    System.out.println("   [OK] Served " + value + " from the front. "
                            + queue.size() + " waiting.");
                    break;
                }
                case 3: {
                    if (queue.isEmpty()) {
                        System.out.println("   ! The queue is empty, there is nothing at the front.");
                        break;
                    }
                    System.out.println("   Front of the queue: " + queue.peek() + " (not removed)");
                    break;
                }
                case 4: {
                    if (queue.isEmpty()) {
                        System.out.println("   ! The queue is empty.");
                        break;
                    }
                    int value = input.readInt("   Value to search for: ");
                    long start = System.nanoTime();
                    int position = queue.search(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Search", "Queue scan", queue.getLastStepCount(), elapsed,
                            position >= 0 ? ("Position " + position) : "Not found", "O(n)");
                    System.out.println(position >= 0
                            ? "   [FOUND] " + value + " is at position " + position + " from the front."
                            : "   [NOT FOUND] " + value + " is not in the queue.");
                    break;
                }
                case 5:
                    System.out.println(queue.display());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    // ------------------------------------------------------ 4. LINKED LIST

    private void linkedListMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("LINKED LIST OPERATIONS", "Member 3 - M.I.M Arshad");
            System.out.println("     1. Insert at head");
            System.out.println("     2. Insert at tail");
            System.out.println("     3. Insert at position");
            System.out.println("     4. Delete by value");
            System.out.println("     5. Delete at position");
            System.out.println("     6. Search");
            System.out.println("     7. Reverse the list");
            System.out.println("     8. Display list");
            System.out.println("     9. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-9): ", 1, 9);
            System.out.println();

            switch (choice) {
                case 1: {
                    int value = input.readInt("   Value to insert at head: ");
                    long start = System.nanoTime();
                    linkedList.insertAtHead(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Insert", "List (head)", linkedList.getLastStepCount(), elapsed,
                            "Inserted " + value, "O(1)");
                    System.out.println("   [OK] " + value + " is the new head. Size " + linkedList.size() + ".");
                    break;
                }
                case 2: {
                    int value = input.readInt("   Value to insert at tail: ");
                    long start = System.nanoTime();
                    linkedList.insertAtTail(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Insert", "List (tail)", linkedList.getLastStepCount(), elapsed,
                            "Inserted " + value, "O(n)");
                    System.out.println("   [OK] " + value + " added at the tail after "
                            + linkedList.getLastStepCount() + " step(s).");
                    break;
                }
                case 3: {
                    int position = input.readInt("   Position (0.." + linkedList.size() + "): ",
                            0, linkedList.size());
                    int value = input.readInt("   Value to insert: ");
                    long start = System.nanoTime();
                    boolean ok = linkedList.insertAt(position, value);
                    long elapsed = System.nanoTime() - start;
                    if (ok) {
                        tracker.record("Insert", "List (position)", linkedList.getLastStepCount(),
                                elapsed, "Inserted at " + position, "O(n)");
                        System.out.println("   [OK] " + value + " inserted at position " + position + ".");
                    } else {
                        System.out.println("   ! Invalid position.");
                    }
                    break;
                }
                case 4: {
                    if (emptyListGuard()) break;
                    int value = input.readInt("   Value to delete: ");
                    long start = System.nanoTime();
                    int position = linkedList.deleteValue(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Delete", "List (by value)", linkedList.getLastStepCount(), elapsed,
                            position >= 0 ? ("Removed from " + position) : "Value not found", "O(n)");
                    System.out.println(position >= 0
                            ? "   [OK] " + value + " removed from position " + position + "."
                            : "   ! " + value + " is not in the list.");
                    break;
                }
                case 5: {
                    if (emptyListGuard()) break;
                    int position = input.readInt("   Position to delete (0.." + (linkedList.size() - 1) + "): ",
                            0, linkedList.size() - 1);
                    long start = System.nanoTime();
                    int removed = linkedList.deleteAt(position);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Delete", "List (position)", linkedList.getLastStepCount(),
                            elapsed, "Removed " + removed, "O(n)");
                    System.out.println("   [OK] " + removed + " removed from position " + position + ".");
                    break;
                }
                case 6: {
                    if (emptyListGuard()) break;
                    int value = input.readInt("   Value to search for: ");
                    long start = System.nanoTime();
                    int position = linkedList.search(value);
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Search", "List scan", linkedList.getLastStepCount(), elapsed,
                            position >= 0 ? ("Found at " + position) : "Not found", "O(n)");
                    System.out.println(position >= 0
                            ? "   [FOUND] " + value + " is at position " + position
                              + " after " + linkedList.getLastStepCount() + " step(s)."
                            : "   [NOT FOUND] " + value + " is not in the list ("
                              + linkedList.getLastStepCount() + " steps).");
                    break;
                }
                case 7: {
                    if (emptyListGuard()) break;
                    long start = System.nanoTime();
                    linkedList.reverse();
                    long elapsed = System.nanoTime() - start;
                    tracker.record("Reverse", "Linked List", linkedList.getLastStepCount(),
                            elapsed, "Reversed " + linkedList.size() + " nodes", "O(n)");
                    System.out.println("   [OK] List reversed by re-pointing every link.");
                    System.out.println(linkedList.display());
                    break;
                }
                case 8:
                    System.out.println(linkedList.display());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    private boolean emptyListGuard() {
        if (linkedList.isEmpty()) {
            System.out.println("   ! The linked list is empty. Insert some values first.");
            pause();
            return true;
        }
        return false;
    }

    // -------------------------------------------------------- 5. SEARCHING

    private void searchingMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("SEARCHING OPERATIONS", "Member 1 - M.H.M Ijas");
            System.out.println("   Searching runs on the ARRAY component, which shows how the");
            System.out.println("   modules integrate: one data set, two different algorithms.");
            System.out.println();
            System.out.println("     1. Linear Search");
            System.out.println("     2. Binary Search (needs sorted data)");
            System.out.println("     3. Compare both on the same value");
            System.out.println("     4. Load a larger sample data set (50 values)");
            System.out.println("     5. Display the current data set");
            System.out.println("     6. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-6): ", 1, 6);
            System.out.println();

            switch (choice) {
                case 1: {
                    if (emptyArrayGuard()) break;
                    int target = input.readInt("   Value to search for: ");
                    SearchOutcome outcome = SearchAlgorithms.linearSearch(array.toArray(), target);
                    reportSearch(outcome, target);
                    break;
                }
                case 2: {
                    if (emptyArrayGuard()) break;
                    if (!array.isSorted()) {
                        System.out.println("   ! Binary search needs SORTED data, and the array is not sorted.");
                        if (!input.readYesNo("   Sort it now? (Y/N): ")) {
                            System.out.println("\n   Binary search cancelled - it would give wrong answers.");
                            break;
                        }
                        long sortSteps = array.sort();
                        System.out.println("\n   Sorted in " + sortSteps + " comparison(s).");
                        System.out.println(array.display());
                        System.out.println();
                    }
                    int target = input.readInt("   Value to search for: ");
                    SearchOutcome outcome = SearchAlgorithms.binarySearch(array.toArray(), target);
                    reportSearch(outcome, target);
                    break;
                }
                case 3:
                    compareSearches();
                    break;
                case 4:
                    loadLargeSample();
                    System.out.println("   [OK] 50 values loaded (unsorted).");
                    System.out.println(array.display());
                    break;
                case 5:
                    System.out.println(array.display());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    private void reportSearch(SearchOutcome outcome, int target) {
        tracker.record("Search", outcome.getAlgorithm(), outcome.getSteps(),
                outcome.getNanoSeconds(), outcome.describe(), outcome.getComplexity());
        System.out.println("   Algorithm  : " + outcome.getAlgorithm()
                + "  " + outcome.getComplexity());
        System.out.println("   Target     : " + target);
        System.out.println("   Result     : " + outcome.describe());
        System.out.println("   Steps      : " + outcome.getSteps() + " comparison(s)");
        System.out.println("   Time       : " + outcome.getNanoSeconds() + " ns");
    }

    private void compareSearches() {
        if (array.isEmpty()) {
            System.out.println("   ! The array is empty. Load the sample data first (option 4).");
            return;
        }
        int target = input.readInt("   Value to search for with BOTH algorithms: ");

        int[] raw = array.toArray();
        SearchOutcome linear = SearchAlgorithms.linearSearch(raw, target);

        int[] sorted = SearchAlgorithms.sortedCopy(raw);
        SearchOutcome binary = SearchAlgorithms.binarySearch(sorted, target);

        tracker.record("Search", linear.getAlgorithm(), linear.getSteps(),
                linear.getNanoSeconds(), linear.describe(), linear.getComplexity());
        tracker.record("Search", binary.getAlgorithm(), binary.getSteps(),
                binary.getNanoSeconds(), binary.describe(), binary.getComplexity());

        System.out.println();
        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + TABLE_HEAD);
        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + new OperationResult("Search", linear.getAlgorithm(),
                linear.getSteps(), linear.getNanoSeconds(), linear.describe(),
                linear.getComplexity()).toRow());
        System.out.println("   " + new OperationResult("Search", binary.getAlgorithm(),
                binary.getSteps(), binary.getNanoSeconds(), binary.describe(),
                binary.getComplexity()).toRow());
        System.out.println("   " + TABLE_LINE);

        System.out.println();
        System.out.println("   WHY THE RESULTS DIFFER");
        System.out.println("   Data set size (n) = " + raw.length);
        System.out.println("   Linear search checks one element at a time, so in the worst case");
        System.out.println("   it performs n comparisons. Binary search halves the remaining");
        System.out.println("   range on every comparison, so it needs about log2(n) = "
                + (int) Math.ceil(Math.log(Math.max(raw.length, 2)) / Math.log(2)) + " at most.");
        System.out.println();
        if (binary.getSteps() < linear.getSteps()) {
            System.out.println("   Here binary search used " + binary.getSteps() + " step(s) against "
                    + linear.getSteps() + " for linear search.");
        } else if (binary.getSteps() > linear.getSteps()) {
            System.out.println("   Here linear search happened to win (" + linear.getSteps()
                    + " vs " + binary.getSteps() + ") because the target sits near the");
            System.out.println("   front of the unsorted array. That is luck, not efficiency -");
            System.out.println("   on average and in the worst case binary search still wins.");
        } else {
            System.out.println("   Both used " + linear.getSteps()
                    + " step(s) on this particular target.");
        }
        System.out.println();
        System.out.println("   The trade-off: binary search needs the data sorted first, and");
        System.out.println("   sorting costs O(n^2) with bubble sort. For a single search on");
        System.out.println("   unsorted data, linear search can be the better choice; for many");
        System.out.println("   repeated searches, sorting once and using binary search wins.");
        System.out.println("   Note the nanosecond timings are far less reliable than the step");
        System.out.println("   counts at this data size - JVM warm-up alone can dominate them.");
    }

    private void loadLargeSample() {
        array.clear();
        int seed = 7;
        for (int i = 0; i < 50; i++) {
            seed = (seed * 31 + 17) % 997;        // simple repeatable generator
            array.insert(seed % 500);
        }
    }

    // ------------------------------------------------------------ 6. GRAPH

    private void graphMenu() {
        boolean inMenu = true;
        while (inMenu) {
            header("GRAPH OPERATIONS", "Member 4 - M.N.M Nafeel");
            System.out.println("     1. Add Vertex");
            System.out.println("     2. Add Edge");
            System.out.println("     3. Remove Vertex");
            System.out.println("     4. Remove Edge");
            System.out.println("     5. Display Graph");
            System.out.println("     6. BFS Traversal");
            System.out.println("     7. DFS Traversal");
            System.out.println("     8. Compare BFS and DFS");
            System.out.println("     9. Shortest path between two vertices");
            System.out.println("    10. Load sample graph");
            System.out.println("    11. Return to Main Menu");
            int choice = input.readInt("   Enter your choice (1-11): ", 1, 11);
            System.out.println();

            switch (choice) {
                case 1: {
                    String name = input.readName("   Vertex name: ");
                    if (graph.addVertex(name)) {
                        System.out.println("   [OK] Vertex '" + name + "' added. Total: "
                                + graph.getVertexCount() + ".");
                    } else {
                        System.out.println("   ! '" + name + "' already exists. Duplicates are not allowed.");
                    }
                    break;
                }
                case 2: {
                    if (graph.getVertexCount() < 2) {
                        System.out.println("   ! At least two vertices are needed before an edge can be added.");
                        break;
                    }
                    listVertices();
                    String from = input.readName("\n   From vertex: ");
                    String to = input.readName("   To vertex  : ");
                    int weight = input.readInt("   Weight     : ", 1, 100000);
                    int result = graph.addEdge(from, to, weight);
                    System.out.println();
                    switch (result) {
                        case 0:
                            System.out.println("   [OK] Edge added: " + from + " <-> " + to
                                    + " (weight " + weight + "). Total edges: " + graph.getEdgeCount() + ".");
                            break;
                        case 1:
                            System.out.println("   ! One or both vertices do not exist. Add them first.");
                            break;
                        case 2:
                            System.out.println("   ! An edge between these two vertices already exists.");
                            break;
                        default:
                            System.out.println("   ! A vertex cannot be connected to itself.");
                    }
                    break;
                }
                case 3: {
                    if (emptyGraphGuard()) break;
                    listVertices();
                    String name = input.readName("\n   Vertex to remove: ");
                    if (!graph.hasVertex(name)) {
                        System.out.println("\n   ! '" + name + "' is not in the graph.");
                        break;
                    }
                    int attached = graph.neighbours(name).length;
                    if (attached > 0) {
                        System.out.println("\n   This vertex has " + attached
                                + " edge(s); they will be removed with it.");
                        if (!input.readYesNo("   Continue? (Y/N): ")) {
                            System.out.println("\n   Cancelled.");
                            break;
                        }
                    }
                    graph.removeVertex(name);
                    System.out.println("\n   [OK] Vertex and all its edges removed.");
                    break;
                }
                case 4: {
                    if (graph.getEdgeCount() == 0) {
                        System.out.println("   ! There are no edges to remove.");
                        break;
                    }
                    System.out.print(graph.adjacencyListView());
                    String from = input.readName("\n   From vertex: ");
                    String to = input.readName("   To vertex  : ");
                    int result = graph.removeEdge(from, to);
                    System.out.println();
                    if (result == 0) {
                        System.out.println("   [OK] Edge removed between " + from + " and " + to + ".");
                    } else if (result == 1) {
                        System.out.println("   ! One or both vertices do not exist.");
                    } else {
                        System.out.println("   ! There is no direct edge between these two vertices.");
                    }
                    break;
                }
                case 5: {
                    if (emptyGraphGuard()) break;
                    System.out.println("   ADJACENCY LIST");
                    System.out.print(graph.adjacencyListView());
                    System.out.println("\n   ADJACENCY MATRIX (1 = direct edge)");
                    System.out.print(graph.adjacencyMatrixView());
                    System.out.println("   Vertices: " + graph.getVertexCount()
                            + " | Edges: " + graph.getEdgeCount());
                    break;
                }
                case 6: {
                    if (emptyGraphGuard()) break;
                    listVertices();
                    String start = input.readName("\n   Start vertex: ");
                    if (!graph.hasVertex(start)) {
                        System.out.println("\n   ! '" + start + "' is not in the graph.");
                        break;
                    }
                    long t0 = System.nanoTime();
                    String[] order = graph.bfs(start);
                    long elapsed = System.nanoTime() - t0;
                    tracker.record("Graph Traversal", "BFS", graph.getLastStepCount(), elapsed,
                            order.length + " vertices visited", "O(V+E)");
                    System.out.println();
                    printOrder("BFS visit order (level by level, uses a QUEUE)", order);
                    System.out.println("   Steps: " + graph.getLastStepCount()
                            + "   Time: " + elapsed + " ns");
                    reportUnreachable(start, order.length);
                    break;
                }
                case 7: {
                    if (emptyGraphGuard()) break;
                    listVertices();
                    String start = input.readName("\n   Start vertex: ");
                    if (!graph.hasVertex(start)) {
                        System.out.println("\n   ! '" + start + "' is not in the graph.");
                        break;
                    }
                    long t0 = System.nanoTime();
                    String[] order = graph.dfs(start);
                    long elapsed = System.nanoTime() - t0;
                    tracker.record("Graph Traversal", "DFS", graph.getLastStepCount(), elapsed,
                            order.length + " vertices visited", "O(V+E)");
                    System.out.println();
                    printOrder("DFS visit order (deep first, uses a STACK)", order);
                    System.out.println("   Steps: " + graph.getLastStepCount()
                            + "   Time: " + elapsed + " ns");
                    reportUnreachable(start, order.length);
                    break;
                }
                case 8:
                    compareTraversals();
                    break;
                case 9: {
                    if (emptyGraphGuard()) break;
                    listVertices();
                    String from = input.readName("\n   From vertex: ");
                    String to = input.readName("   To vertex  : ");
                    if (!graph.hasVertex(from) || !graph.hasVertex(to)) {
                        System.out.println("\n   ! One or both vertices are not in the graph.");
                        break;
                    }
                    long t0 = System.nanoTime();
                    String[] path = graph.shortestPath(from, to);
                    long elapsed = System.nanoTime() - t0;
                    System.out.println();
                    if (path == null) {
                        tracker.record("Shortest Path", "BFS path", graph.getLastStepCount(),
                                elapsed, "No route", "O(V+E)");
                        System.out.println("   ! There is no path between " + from + " and " + to + ".");
                    } else {
                        tracker.record("Shortest Path", "BFS path", graph.getLastStepCount(),
                                elapsed, (path.length - 1) + " edges", "O(V+E)");
                        System.out.println("   Shortest path (" + (path.length - 1) + " edge(s)):");
                        System.out.println("   " + join(path, " -> "));
                    }
                    break;
                }
                case 10:
                    loadSampleGraph();
                    System.out.println("   [OK] Sample graph loaded: " + graph.getVertexCount()
                            + " vertices, " + graph.getEdgeCount() + " edges.");
                    System.out.print(graph.adjacencyListView());
                    break;
                default:
                    inMenu = false;
                    continue;
            }
            pause();
        }
    }

    private boolean emptyGraphGuard() {
        if (graph.isEmpty()) {
            System.out.println("   ! The graph has no vertices. Add some, or load the sample (option 10).");
            pause();
            return true;
        }
        return false;
    }

    private void listVertices() {
        System.out.println("   Vertices: " + join(graph.vertexNames(), " | "));
    }

    private void reportUnreachable(String start, int reached) {
        if (reached < graph.getVertexCount()) {
            System.out.println("\n   Note: " + (graph.getVertexCount() - reached)
                    + " vertex/vertices cannot be reached from " + start
                    + " - the graph is not fully connected.");
        }
    }

    private void compareTraversals() {
        if (emptyGraphGuard()) {
            return;
        }
        listVertices();
        String start = input.readName("\n   Start vertex for both traversals: ");
        if (!graph.hasVertex(start)) {
            System.out.println("\n   ! '" + start + "' is not in the graph.");
            return;
        }

        long t0 = System.nanoTime();
        String[] bfsOrder = graph.bfs(start);
        long bfsTime = System.nanoTime() - t0;
        long bfsSteps = graph.getLastStepCount();

        long t1 = System.nanoTime();
        String[] dfsOrder = graph.dfs(start);
        long dfsTime = System.nanoTime() - t1;
        long dfsSteps = graph.getLastStepCount();

        tracker.record("Graph Traversal", "BFS", bfsSteps, bfsTime,
                bfsOrder.length + " vertices", "O(V+E)");
        tracker.record("Graph Traversal", "DFS", dfsSteps, dfsTime,
                dfsOrder.length + " vertices", "O(V+E)");

        System.out.println();
        printOrder("BFS order", bfsOrder);
        printOrder("DFS order", dfsOrder);

        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + TABLE_HEAD);
        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + new OperationResult("Graph Traversal", "BFS", bfsSteps,
                bfsTime, bfsOrder.length + " vertices", "O(V+E)").toRow());
        System.out.println("   " + new OperationResult("Graph Traversal", "DFS", dfsSteps,
                dfsTime, dfsOrder.length + " vertices", "O(V+E)").toRow());
        System.out.println("   " + TABLE_LINE);

        System.out.println();
        System.out.println("   WHY THE RESULTS DIFFER");
        System.out.println("   Both traversals are O(V+E): each visits every reachable vertex");
        System.out.println("   once and looks at every edge of those vertices, so their step");
        System.out.println("   counts are very close - here " + bfsSteps + " against " + dfsSteps + ".");
        System.out.println();
        System.out.println("   What differs is the ORDER, and that comes from the structure each");
        System.out.println("   one uses. BFS holds pending vertices in a QUEUE, so the oldest");
        System.out.println("   waits first and the traversal spreads level by level. DFS uses a");
        System.out.println("   STACK, so the newest goes first and the traversal dives deep");
        System.out.println("   before backtracking.");
        System.out.println();
        System.out.println("   That is why BFS finds the path with the fewest edges, while DFS");
        System.out.println("   reaches a distant vertex sooner but by no guaranteed-short route.");
    }

    private void loadSampleGraph() {
        graph.clear();
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (String v : vertices) {
            graph.addVertex(v);
        }
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 3);
        graph.addEdge("B", "D", 7);
        graph.addEdge("C", "D", 2);
        graph.addEdge("D", "E", 5);
        graph.addEdge("E", "F", 6);
        graph.addEdge("C", "F", 9);
        graph.addEdge("F", "G", 1);
        graph.addEdge("B", "E", 8);
        // H is added deliberately with no edges, to demonstrate a disconnected vertex
    }

    private void printOrder(String title, String[] order) {
        System.out.println("   " + title + " (" + order.length + " vertices):");
        System.out.println("   " + join(order, " -> "));
        System.out.println();
    }

    private String join(String[] parts, String separator) {
        if (parts.length == 0) {
            return "(none)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i]);
            if (i < parts.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    // ---------------------------------------------- 7. PERFORMANCE COMPARISON

    private void performanceTable() {
        header("PERFORMANCE COMPARISON", "Member 3 - M.I.M Arshad");
        OperationResult[] searches = tracker.filterByOperation("Search");
        OperationResult[] traversals = tracker.filterByOperation("Graph Traversal");

        if (searches.length == 0 && traversals.length == 0) {
            System.out.println("   No search or traversal has been run yet, so there is nothing");
            System.out.println("   to compare. Try menu 5 (Searching) and menu 6 (Graph) first.");
            pause();
            return;
        }

        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + TABLE_HEAD);
        System.out.println("   " + TABLE_LINE);
        for (OperationResult r : searches) {
            System.out.println("   " + r.toRow());
        }
        for (OperationResult r : traversals) {
            System.out.println("   " + r.toRow());
        }
        System.out.println("   " + TABLE_LINE);

        System.out.println();
        System.out.println("   COMPLEXITY REFERENCE");
        System.out.println("     Linear Search   O(n)       checks each element in turn");
        System.out.println("     Binary Search   O(log n)   halves the range each comparison, needs sorted data");
        System.out.println("     BFS             O(V+E)     queue based, visits level by level");
        System.out.println("     DFS             O(V+E)     stack based, dives deep then backtracks");
        System.out.println("     Bubble Sort     O(n^2)     compares neighbouring pairs repeatedly");
        System.out.println();
        System.out.println("   HOW TO READ THIS TABLE");
        System.out.println("   Step counts are the honest measure: they are the actual number of");
        System.out.println("   comparisons or visits the algorithm performed, and they do not");
        System.out.println("   change between runs. The nanosecond timings do change, because");
        System.out.println("   JVM warm-up, just-in-time compilation and the operating system");
        System.out.println("   all interfere at this scale. A faster time with more steps is");
        System.out.println("   measurement noise, not a faster algorithm.");
        pause();
    }

    // --------------------------------------------------- 8. ALL RESULTS

    private void displayAllResults() {
        header("ALL RECORDED RESULTS", "Integration - all members");
        OperationResult[] all = tracker.all();
        if (all.length == 0) {
            System.out.println("   Nothing has been recorded yet. Use the other menus first.");
            pause();
            return;
        }
        System.out.println("   " + TABLE_LINE);
        System.out.println("   " + TABLE_HEAD);
        System.out.println("   " + TABLE_LINE);
        for (OperationResult r : all) {
            System.out.println("   " + r.toRow());
        }
        System.out.println("   " + TABLE_LINE);
        System.out.println("   Total operations recorded: " + all.length);

        System.out.println();
        System.out.println("   CURRENT STATE OF EVERY STRUCTURE");
        System.out.println("     Array       : " + array.size() + " element(s), sorted: "
                + (array.isSorted() ? "yes" : "no"));
        System.out.println("     Stack       : " + stack.size() + " item(s)");
        System.out.println("     Queue       : " + queue.size() + " item(s)");
        System.out.println("     Linked List : " + linkedList.size() + " node(s)");
        System.out.println("     Graph       : " + graph.getVertexCount() + " vertices, "
                + graph.getEdgeCount() + " edges");

        if (input.readYesNo("\n   Clear the recorded results? (Y/N): ")) {
            tracker.clear();
            System.out.println("\n   Results cleared.");
        }
        pause();
    }

    // ------------------------------------------------------------- 9. EXIT

    private boolean confirmExit() {
        if (input.readYesNo("   Are you sure you want to exit? (Y/N): ")) {
            System.out.println();
            System.out.println("   Session summary:");
            System.out.println("     Operations recorded : " + tracker.size());
            System.out.println("     Array size          : " + array.size());
            System.out.println("     Stack size          : " + stack.size());
            System.out.println("     Queue size          : " + queue.size());
            System.out.println("     Linked list size    : " + linkedList.size());
            System.out.println("     Graph               : " + graph.getVertexCount()
                    + " vertices, " + graph.getEdgeCount() + " edges");
            System.out.println();
            System.out.println("   Thank you for using the Data Structure & Graph Analyzer.");
            System.out.println(BAR);
            return false;
        }
        return true;
    }

    /** Loads sample data into every structure, for the demonstration video. */
    public void loadAllSampleData() {
        loadSampleArray();
        loadSampleGraph();
        int[] values = {15, 8, 42, 23, 4};
        for (int v : values) {
            stack.push(v);
            queue.enqueue(v);
            linkedList.insertAtTail(v);
        }
    }
}
