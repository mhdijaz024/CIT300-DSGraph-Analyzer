package app;

import algorithms.SearchAlgorithms;
import algorithms.SearchAlgorithms.SearchOutcome;
import performance.OperationResult;
import performance.PerformanceTracker;
import structures.DynamicArray;
import structures.Graph;
import structures.IntQueue;
import structures.IntStack;
import structures.SinglyLinkedList;

/**
 * Automated test harness that exercises every component directly, without
 * going through the menu. Run it with:
 *
 *     java -cp bin app.StructureTest
 *
 * It prints one line per check and a PASS/FAIL summary, which is useful
 * evidence of testing for the assignment report and the demonstration video.
 */
public class StructureTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== CIT300 Assignment 2 - Component Test Suite ===\n");
        testArray();
        testStack();
        testQueue();
        testLinkedList();
        testSearching();
        testGraph();
        testPerformanceTracker();

        System.out.println("\n----------------------------------------------------");
        System.out.printf("   TOTAL: %d passed, %d failed%n", passed, failed);
        System.out.println(failed == 0 ? "   RESULT: ALL TESTS PASSED" : "   RESULT: FAILURES PRESENT");
        System.out.println("----------------------------------------------------");
    }

    private static void check(String label, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("   [PASS] " + label);
        } else {
            failed++;
            System.out.println("   [FAIL] " + label);
        }
    }

    // ------------------------------------------------------------------ array

    private static void testArray() {
        System.out.println("1. Array (Member 1 - M.H.M Ijas)");
        DynamicArray a = new DynamicArray(2);
        check("new array is empty", a.isEmpty());
        check("search on an empty array returns -1", a.search(5) == -1);
        check("delete on an empty array returns -1", a.deleteValue(5) == -1);

        a.insert(30);
        a.insert(10);
        a.insert(20);
        check("size is 3 after three inserts", a.size() == 3);
        check("array grew past its initial capacity of 2", a.capacity() >= 3);
        check("search finds an existing value", a.search(10) == 1);
        check("search returns -1 for a missing value", a.search(99) == -1);
        check("search step count equals the index scanned", a.getLastStepCount() == 3);

        check("insert at index 0 works", a.insertAt(0, 5));
        check("value landed at index 0", a.get(0) == 5);
        check("later elements shifted right", a.get(1) == 30);
        check("insert at an invalid index is rejected", !a.insertAt(99, 1));

        check("delete by value returns the index", a.deleteValue(30) == 1);
        check("size dropped to 3", a.size() == 3);
        check("deleting a missing value returns -1", a.deleteValue(99) == -1);

        a.insert(1);                           // makes the array [5, 10, 20, 1]
        check("unsorted array reports unsorted", !a.isSorted());
        a.sort();
        check("array is sorted after sort()", a.isSorted());
        int[] sorted = a.toArray();
        check("sorted order is correct",
                sorted[0] == 1 && sorted[1] == 5 && sorted[2] == 10 && sorted[3] == 20);
        check("sorting an already-sorted array exits early", a.sort() < 10);

        boolean threw = false;
        try {
            a.deleteAt(99);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        check("deleteAt with a bad index throws", threw);
        System.out.println();
    }

    // ------------------------------------------------------------------ stack

    private static void testStack() {
        System.out.println("2. Stack - LIFO (Member 2 - Z. Isham)");
        IntStack s = new IntStack();
        check("new stack is empty", s.isEmpty());

        boolean threw = false;
        try {
            s.pop();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("pop on an empty stack is rejected (underflow)", threw);

        threw = false;
        try {
            s.peek();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("peek on an empty stack is rejected", threw);

        s.push(1);
        s.push(2);
        s.push(3);
        check("size is 3", s.size() == 3);
        check("peek returns the newest value", s.peek() == 3);
        check("peek did not remove it", s.size() == 3);
        check("pop returns LIFO order", s.pop() == 3);
        check("next pop returns the second value", s.pop() == 2);
        check("size is 1 after two pops", s.size() == 1);
        s.push(9);
        check("search finds the top at depth 0", s.search(9) == 0);
        check("search finds a deeper value", s.search(1) == 1);
        check("search returns -1 for a missing value", s.search(77) == -1);
        s.pop();
        s.pop();
        check("stack is empty again", s.isEmpty());
        s.push(5);
        check("stack is reusable after being emptied", s.size() == 1);
        System.out.println();
    }

    // ------------------------------------------------------------------ queue

    private static void testQueue() {
        System.out.println("3. Queue - FIFO (Member 2 - Z. Isham)");
        IntQueue q = new IntQueue();
        check("new queue is empty", q.isEmpty());

        boolean threw = false;
        try {
            q.dequeue();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("dequeue on an empty queue is rejected (underflow)", threw);

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        check("size is 3", q.size() == 3);
        check("peek returns the earliest arrival", q.peek() == 1);
        check("dequeue returns FIFO order", q.dequeue() == 1);
        check("next dequeue returns the second value", q.dequeue() == 2);
        check("search finds a value by position", q.search(3) == 0);
        check("search returns -1 for a missing value", q.search(77) == -1);
        q.dequeue();
        check("queue is empty after draining", q.isEmpty());
        q.enqueue(8);
        check("queue is reusable after draining (rear pointer reset)", q.size() == 1 && q.peek() == 8);
        System.out.println();
    }

    // ------------------------------------------------------------ linked list

    private static void testLinkedList() {
        System.out.println("4. Singly Linked List (Member 3 - M.I.M Arshad)");
        SinglyLinkedList list = new SinglyLinkedList();
        check("new list is empty", list.isEmpty());
        check("search on an empty list returns -1", list.search(1) == -1);
        check("delete on an empty list returns -1", list.deleteValue(1) == -1);

        list.insertAtHead(20);
        list.insertAtHead(10);
        check("insert at head puts the newest first", list.toArray()[0] == 10);
        check("head insert takes one step", list.getLastStepCount() == 1);

        list.insertAtTail(30);
        check("insert at tail appends", list.toArray()[2] == 30);
        check("tail insert walks the list", list.getLastStepCount() > 1);
        check("size is 3", list.size() == 3);

        check("insert at a middle position works", list.insertAt(1, 15));
        check("value landed at position 1", list.toArray()[1] == 15);
        check("insert at an invalid position is rejected", !list.insertAt(99, 1));

        check("search finds the head", list.search(10) == 0);
        check("search finds a middle node", list.search(20) == 2);
        check("search returns -1 for a missing value", list.search(99) == -1);

        check("delete the head", list.deleteValue(10) == 0);
        check("delete a middle node", list.deleteValue(20) == 1);
        check("size is 2", list.size() == 2);
        check("deleting a missing value returns -1", list.deleteValue(99) == -1);

        list.clear();
        list.insertAtTail(1);
        list.insertAtTail(2);
        list.insertAtTail(3);
        list.reverse();
        int[] reversed = list.toArray();
        check("reverse flips the order", reversed[0] == 3 && reversed[2] == 1);
        check("reverse keeps the size", list.size() == 3);

        boolean threw = false;
        try {
            list.deleteAt(99);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        check("deleteAt with a bad position throws", threw);
        System.out.println();
    }

    // -------------------------------------------------------------- searching

    private static void testSearching() {
        System.out.println("5. Searching - Linear vs Binary (Member 1 - M.H.M Ijas)");
        int[] sorted = new int[100];
        for (int i = 0; i < 100; i++) {
            sorted[i] = i * 2;                 // 0, 2, 4, ... 198
        }

        SearchOutcome linear = SearchAlgorithms.linearSearch(sorted, 198);
        check("linear search finds the last element", linear.getIndex() == 99);
        check("linear search needed n steps in the worst case", linear.getSteps() == 100);
        check("linear search reports O(n)", "O(n)".equals(linear.getComplexity()));

        SearchOutcome binary = SearchAlgorithms.binarySearch(sorted, 198);
        check("binary search finds the same element", binary.getIndex() == 99);
        check("binary search needed far fewer steps", binary.getSteps() <= 8);
        check("binary search reports O(log n)", "O(log n)".equals(binary.getComplexity()));
        check("binary beats linear on the worst case", binary.getSteps() < linear.getSteps());

        SearchOutcome missLinear = SearchAlgorithms.linearSearch(sorted, 777);
        check("linear search reports a miss", !missLinear.isFound());
        SearchOutcome missBinary = SearchAlgorithms.binarySearch(sorted, 777);
        check("binary search reports a miss", !missBinary.isFound());
        check("binary search terminates on a miss", missBinary.getSteps() <= 8);

        SearchOutcome first = SearchAlgorithms.binarySearch(sorted, 0);
        check("binary search finds the first element", first.getIndex() == 0);

        check("isSorted accepts sorted data", SearchAlgorithms.isSorted(sorted));
        check("isSorted rejects unsorted data",
                !SearchAlgorithms.isSorted(new int[]{3, 1, 2}));
        int[] tidy = SearchAlgorithms.sortedCopy(new int[]{5, 3, 9, 1});
        check("sortedCopy returns ascending order",
                tidy[0] == 1 && tidy[1] == 3 && tidy[2] == 5 && tidy[3] == 9);

        SearchOutcome empty = SearchAlgorithms.binarySearch(new int[0], 5);
        check("binary search on an empty array is safe", !empty.isFound());
        System.out.println();
    }

    // ------------------------------------------------------------------ graph

    private static void testGraph() {
        System.out.println("6. Graph - adjacency list, BFS/DFS (Member 4 - M.N.M Nafeel)");
        Graph g = new Graph();
        check("new graph is empty", g.isEmpty());
        check("BFS on an empty graph returns null", g.bfs("A") == null);

        check("add a vertex", g.addVertex("A"));
        check("duplicate vertex is rejected", !g.addVertex("a"));
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("Z");                       // deliberately left unconnected
        check("5 vertices recorded", g.getVertexCount() == 5);

        check("add an edge", g.addEdge("A", "B", 5) == 0);
        check("duplicate edge is rejected", g.addEdge("B", "A", 5) == 2);
        check("self-loop is rejected", g.addEdge("A", "A", 1) == 3);
        check("edge to a missing vertex is rejected", g.addEdge("A", "Nowhere", 1) == 1);
        g.addEdge("B", "C", 2);
        g.addEdge("C", "D", 7);
        check("3 edges recorded", g.getEdgeCount() == 3);
        check("edges are undirected", g.hasEdge("B", "A") && g.hasEdge("A", "B"));
        check("neighbours are listed", g.neighbours("B").length == 2);

        String[] bfs = g.bfs("A");
        check("BFS reaches the 4 connected vertices", bfs.length == 4);
        check("BFS starts at the origin", "A".equals(bfs[0]));
        check("BFS visits the nearest neighbour second", "B".equals(bfs[1]));
        check("BFS counts its steps", g.getLastStepCount() > 0);
        check("BFS from an unknown vertex returns null", g.bfs("Nowhere") == null);
        check("isolated vertex is not reached", !contains(bfs, "Z"));

        String[] dfs = g.dfs("A");
        check("DFS reaches the same 4 vertices", dfs.length == 4);
        check("DFS visits each vertex exactly once", noDuplicates(dfs));
        check("DFS from an unknown vertex returns null", g.dfs("Nowhere") == null);

        String[] path = g.shortestPath("A", "D");
        check("shortest path found", path != null && path.length == 4);
        check("shortest path starts and ends correctly",
                path != null && "A".equals(path[0]) && "D".equals(path[3]));
        check("no path to an isolated vertex", g.shortestPath("A", "Z") == null);

        check("remove an edge", g.removeEdge("B", "C") == 0);
        check("removing it twice fails", g.removeEdge("B", "C") == 2);
        check("edge count dropped to 2", g.getEdgeCount() == 2);
        check("C and D are now unreachable from A", g.bfs("A").length == 2);

        check("remove a vertex", g.removeVertex("B"));
        check("its edges were cleaned up", !g.hasEdge("A", "B"));
        check("removing a missing vertex fails", !g.removeVertex("B"));
        check("4 vertices remain", g.getVertexCount() == 4);

        // Dense graph: the worst case for the iterative DFS stack.
        Graph dense = new Graph();
        for (int i = 0; i < 12; i++) {
            dense.addVertex("V" + i);
        }
        String[] names = dense.vertexNames();
        for (int i = 0; i < names.length; i++) {
            for (int j = i + 1; j < names.length; j++) {
                dense.addEdge(names[i], names[j], 1);
            }
        }
        check("dense graph has 66 edges", dense.getEdgeCount() == 66);
        check("DFS on a dense graph does not overflow", dense.dfs(names[0]).length == 12);
        check("BFS on a dense graph visits every vertex", dense.bfs(names[0]).length == 12);
        check("BFS and DFS cost the same order of steps on a dense graph",
                Math.abs(stepsOfBfs(dense, names[0]) - stepsOfDfs(dense, names[0])) < 200);
        System.out.println();
    }

    private static long stepsOfBfs(Graph g, String start) {
        g.bfs(start);
        return g.getLastStepCount();
    }

    private static long stepsOfDfs(Graph g, String start) {
        g.dfs(start);
        return g.getLastStepCount();
    }

    private static boolean contains(String[] arr, String value) {
        for (String item : arr) {
            if (item.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private static boolean noDuplicates(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].equalsIgnoreCase(arr[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    // ---------------------------------------------------- performance tracker

    private static void testPerformanceTracker() {
        System.out.println("7. Performance Tracker (Member 3 - M.I.M Arshad)");
        PerformanceTracker t = new PerformanceTracker();
        check("new tracker is empty", t.isEmpty());
        check("filter on an empty tracker returns nothing",
                t.filterByOperation("Search").length == 0);

        t.record("Search", "Linear Search", 100, 5000, "Found at index 99", "O(n)");
        t.record("Search", "Binary Search", 7, 900, "Found at index 99", "O(log n)");
        t.record("Graph Traversal", "BFS", 20, 1200, "8 vertices", "O(V+E)");

        check("3 results recorded", t.size() == 3);
        check("filter by operation works", t.filterByOperation("Search").length == 2);
        check("filter is case-insensitive", t.filterByOperation("search").length == 2);
        check("latestFor finds an algorithm", t.latestFor("BFS") != null);
        check("latestFor returns null for an unknown algorithm", t.latestFor("Nothing") == null);

        OperationResult r = t.filterByOperation("Search")[1];
        check("stored steps are correct", r.getSteps() == 7);
        check("stored complexity is correct", "O(log n)".equals(r.getComplexity()));
        check("milliseconds are derived from nanoseconds", r.getMilliSeconds() > 0);
        check("row formatting produces a table row", r.toRow().startsWith("|"));

        // Force the internal array to grow past its initial capacity of 8.
        for (int i = 0; i < 30; i++) {
            t.record("Insert", "Array (end)", 1, 10, "ok", "O(1)");
        }
        check("tracker grows past its initial capacity", t.size() == 33);
        check("all() returns every result", t.all().length == 33);

        t.clear();
        check("clear empties the tracker", t.isEmpty());
    }
}
