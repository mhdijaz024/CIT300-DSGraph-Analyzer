# Data Structure and Graph Performance Analyzer

**Module:** CIT300 – Data Structures and Algorithms
**Assessment:** Graded Practical Assignment 2 (Week 12) – 10% of the final grade
**Institution:** SLTC Research University
**Language:** Java (console-based, no external libraries)
**Scope:** Weeks 1–11

---

## 1. Project Description

A single integrated Java console application that lets the user work with five
data structures and two search algorithms, and then **measure and compare** how
they perform on the same data.

Every operation records the number of basic steps it performed and how long it
took, so the system can print a performance comparison table showing *why*
binary search beats linear search, and *why* BFS and DFS cost the same but
produce different visit orders.

Every data structure is implemented from scratch. **No `java.util` collection
class is used anywhere** — not for the structures, not for the BFS queue, not
for the DFS stack, not even for the results list inside the performance tracker.
The only `java.util` import in the project is `Scanner`, for reading the console.

---

## 2. Team Members, Responsibilities and Individual Contributions

### Member 1

**Student Name:** M.H.M Ijas
**Student ID:** 23DA2-0675
**Assigned Responsibility:** Array component, Searching component, and project leadership

**Individual Contribution:**
- Implemented the `DynamicArray` class with automatic capacity doubling
- Implemented insert at end, insert at index with right-shift, delete by value, delete by index with left-shift, search and display
- Implemented bubble sort with an early-exit optimisation when a pass makes no swap
- Implemented the `SearchAlgorithms` class containing linear search and binary search
- Added step counting to both search algorithms so they can be compared fairly
- Implemented the guard that refuses binary search on unsorted data, and offers to sort first
- Built menu options 1 (Array Operations) and 5 (Searching Operations)
- Led the integration of all four components and maintained the README

### Member 2

**Student Name:** Z. Isham
**Student ID:** 23DA2-0677
**Assigned Responsibility:** Stack and Queue components

**Individual Contribution:**
- Implemented the `IntStack` class using linked nodes, so capacity is never fixed
- Implemented push, pop, peek, search and display, all O(1) except search
- Implemented stack underflow handling so popping an empty stack is reported, not crashed
- Implemented the `IntQueue` class with separate front and rear pointers, giving O(1) enqueue and dequeue
- Implemented the rear-pointer reset so the queue is reusable after being fully drained
- Implemented queue underflow handling for dequeue and peek on an empty queue
- Built menu options 2 (Stack Operations) and 3 (Queue Operations)

### Member 3

**Student Name:** M.I.M Arshad
**Student ID:** 23DA2-0634
**Assigned Responsibility:** Linked List component and Performance/Complexity demonstration

**Individual Contribution:**
- Implemented the `SinglyLinkedList` class with its private `Node` inner class
- Implemented insert at head (O(1)), insert at tail (O(n)) and insert at a given position
- Implemented delete by value and delete by position, correctly handling the head case
- Implemented search and an in-place reverse that re-points every link in one pass
- Implemented the `OperationResult` class that holds one measurement: operation, algorithm, steps, time, outcome and complexity
- Implemented the `PerformanceTracker` class with its own growable array and filtering by operation
- Built menu options 4 (Linked List Operations), 7 (Performance Comparison) and 8 (Display All Results)
- Wrote the complexity explanations shown in the performance table

### Member 4

**Student Name:** M.N.M Nafeel
**Student ID:** 23DA2-0678
**Assigned Responsibility:** Graph component and traversal algorithms

**Individual Contribution:**
- Implemented the `Graph` class using an **adjacency list** built from hand-written `Vertex` and `Edge` linked structures
- Implemented add vertex, add edge (undirected and weighted), remove vertex and remove edge
- Implemented the cleanup that removes every edge attached to a vertex when that vertex is deleted, so no dangling edge is left behind
- Implemented the adjacency list view and the adjacency matrix view
- Implemented **BFS** traversal using a hand-written queue
- Implemented iterative **DFS** traversal using a hand-written stack
- Sized the DFS stack for the worst case, where a vertex can be pushed once per incoming edge before being marked visited
- Implemented shortest-path search by recording parents during BFS and walking the chain back
- Built menu option 6 (Graph Operations) including the BFS vs DFS comparison

### All Members

**Assigned Responsibility:** Integration, validation, testing, debugging, documentation, GitHub collaboration

**Individual Contribution:**
- Jointly built `MenuUI` (the main menu and all six submenus), `InputValidator` and `Main`
- Jointly wrote `StructureTest`, the 130-check automated test suite
- Jointly produced this README, the test plan, the Git workflow and the demonstration video

Each member can explain and demonstrate their own component. The video is split
along exactly these lines — see `docs/DEMO_VIDEO_SCRIPT.md`.

---

## 3. Technologies Used

| Item | Detail |
|---|---|
| Language | Java (JDK 8 or newer; developed and tested on JDK 21) |
| Application type | Console-based, menu-driven |
| Paradigm | Object-oriented — encapsulation, inner classes, separation of concerns across packages |
| External libraries | **None.** No Maven, no Gradle, no third-party JAR |
| Java standard library use | `java.util.Scanner` for console input only. No collection class is used anywhere. |
| Timing | `System.nanoTime()` for execution measurement |
| Version control | Git and GitHub, with feature branches and pull requests |

---

## 4. Main System Features

### Main menu

```
=================================================================
        DATA STRUCTURE & GRAPH PERFORMANCE ANALYZER
=================================================================
  1. Array Operations            6. Graph Operations
  2. Stack Operations            7. Performance Comparison
  3. Queue Operations            8. Display All Results
  4. Linked List Operations      9. Exit
  5. Searching Operations
```

Each component has its own submenu, as required by the assignment.

| Menu | Submenu options |
|---|---|
| **1. Array** | Insert at end, insert at index, delete by value, delete at index, search, sort, display, load sample data |
| **2. Stack** | Push, pop, peek, search, display |
| **3. Queue** | Enqueue, dequeue, peek/front, search, display |
| **4. Linked List** | Insert at head, insert at tail, insert at position, delete by value, delete at position, search, reverse, display |
| **5. Searching** | Linear search, binary search, **compare both on the same value**, load a 50-value data set, display the data set |
| **6. Graph** | Add vertex, add edge, remove vertex, remove edge, display (list + matrix), BFS, DFS, **compare BFS and DFS**, shortest path, load sample graph |
| **7. Performance Comparison** | The required comparison table plus a complexity reference and an explanation of how to read it |
| **8. Display All Results** | Every recorded operation, plus the live state of all five structures |

### Integration

The searching component runs on the **array component's own data**, so the two
modules are genuinely integrated rather than independent. Every component feeds
the same `PerformanceTracker`, which is what menu options 7 and 8 read from.

### Extra features beyond the minimum

- Bubble sort with early exit, needed before binary search can run safely
- A guard that refuses binary search on unsorted data and offers to sort first
- Shortest-path search on the graph (BFS with parent tracking)
- Adjacency matrix view alongside the adjacency list
- In-place linked list reversal
- A 130-check automated test suite (`app.StructureTest`)
- Detection and reporting of unreachable vertices in a disconnected graph

---

## 5. Instructions for Running the Program

### Requirements

JDK 8 or newer, with `javac` and `java` on the PATH. No build tool is needed.

### Windows

```bat
run.bat
```

### Linux / macOS

```bash
chmod +x run.sh
./run.sh
```

### Manual compile and run

```bash
javac -d bin src/structures/*.java src/algorithms/*.java src/performance/*.java src/ui/*.java src/app/*.java
java -cp bin app.Main
```

On start the program asks whether to load sample data into every structure.
Answer **Y** for the demonstration — it loads a 15-value array, 5 items into the
stack, queue and linked list, and an 8-vertex / 9-edge graph.

### Run the automated test suite

```bash
java -cp bin app.StructureTest
```

Expected final lines:

```
   TOTAL: 130 passed, 0 failed
   RESULT: ALL TESTS PASSED
```

### Command-line flags

| Flag | Effect |
|---|---|
| `--sample` | Load sample data without asking |
| `--empty` | Start with empty structures without asking |
| `--no-pause` | Skip the "Press Enter" pauses (used for automated testing) |

---

## 6. Project Structure

```
DSGraphAnalyzer/
├── README.md                        <- this file
├── run.bat / run.sh                 <- one-click compile and run
├── .gitignore
├── src/
│   ├── structures/
│   │   ├── DynamicArray.java        <- Member 1 : growable array
│   │   ├── IntStack.java            <- Member 2 : LIFO stack
│   │   ├── IntQueue.java            <- Member 2 : FIFO queue
│   │   ├── SinglyLinkedList.java    <- Member 3 : linked list
│   │   └── Graph.java               <- Member 4 : adjacency-list graph, BFS/DFS
│   ├── algorithms/
│   │   └── SearchAlgorithms.java    <- Member 1 : linear and binary search
│   ├── performance/
│   │   ├── OperationResult.java     <- Member 3 : one measurement
│   │   └── PerformanceTracker.java  <- Member 3 : collects measurements
│   ├── ui/
│   │   ├── InputValidator.java      <- all console reads and validation
│   │   └── MenuUI.java              <- main menu and all six submenus
│   └── app/
│       ├── Main.java                <- entry point
│       └── StructureTest.java       <- 130-check automated test suite
└── docs/
    ├── TEST_PLAN.md
    ├── GIT_WORKFLOW.md
    ├── DEMO_VIDEO_SCRIPT.md
    ├── SUBMISSION_CHECKLIST.md
    ├── test_input.txt
    └── test_input_graph.txt
```

---

## 7. Performance and Complexity

The heart of this assignment. Menu option 7 prints this table from real
measurements taken during the session:

```
+------------------+--------------------+----------+--------------+------------+
| Operation        | Algorithm          |    Steps |    Time (ns) | Complexity |
+------------------+--------------------+----------+--------------+------------+
| Search           | Linear Search      |       50 |         1382 | O(n)       |
| Search           | Binary Search      |        6 |         1168 | O(log n)   |
| Graph Traversal  | BFS                |       25 |        21175 | O(V+E)     |
| Graph Traversal  | DFS                |       25 |        12970 | O(V+E)     |
+------------------+--------------------+----------+--------------+------------+
```

*(An actual run on the 50-value sample data set and the 8-vertex sample graph.)*

### Complexity of every operation

| Component | Operation | Complexity | Why |
|---|---|---|---|
| Array | Insert at end | O(1) amortised | Writes to the next free slot; only the occasional resize costs more |
| Array | Insert at index | O(n) | Every later element shifts one place right |
| Array | Delete | O(n) | Every later element shifts one place left |
| Array | Search | O(n) | Checks each element in turn |
| Array | Bubble sort | O(n²) | Compares neighbouring pairs on every pass |
| Stack | Push / Pop / Peek | **O(1)** | Only the top node is touched |
| Stack | Search | O(n) | Walks down the chain |
| Queue | Enqueue / Dequeue / Peek | **O(1)** | Front and rear pointers mean no traversal |
| Queue | Search | O(n) | Walks from front to rear |
| Linked List | Insert at head | **O(1)** | No traversal needed |
| Linked List | Insert at tail | O(n) | Must walk to the last node |
| Linked List | Delete / Search | O(n) | Must find the node first |
| Linked List | Reverse | O(n) | One pass, re-pointing each link |
| Searching | Linear search | O(n) | May check every element |
| Searching | Binary search | **O(log n)** | Halves the range each comparison |
| Graph | Add vertex | O(1) | Inserted at the head of the vertex list |
| Graph | Add edge | O(V + deg) | Finds both vertices, then links in O(1) |
| Graph | Remove vertex | O(V + E) | Every other vertex is checked for edges into it |
| Graph | BFS / DFS | **O(V + E)** | Each reachable vertex visited once, each of its edges examined once |

### Why linear and binary search differ

Linear search makes no assumption about the data, so it may compare against
every element: n comparisons in the worst case. Binary search requires **sorted**
data, and uses that to discard half of the remaining range on every comparison,
so it needs at most ⌈log₂ n⌉ comparisons. On the 50-value sample set that is
50 steps against 6 — and the gap widens as n grows: at n = 1,000,000 it is
1,000,000 against 20.

The trade-off is the sort. Bubble sort costs O(n²), which is more than the
single linear search it would save. So for **one** search on unsorted data,
linear search is the better choice; for **many repeated** searches, sorting once
and then using binary search wins easily.

### Why BFS and DFS differ

Both are O(V + E) and both visit the same set of reachable vertices, so their
step counts come out nearly identical — 25 against 25 on the sample graph. What
differs is the **order**, and that comes directly from the structure each one
uses to hold pending vertices:

- **BFS** uses a **queue** (FIFO), so the oldest pending vertex is taken first
  and the traversal spreads outward level by level. This is why BFS finds the
  path with the fewest edges.
- **DFS** uses a **stack** (LIFO), so the newest pending vertex is taken first
  and the traversal dives as deep as it can before backtracking. DFS can reach a
  distant vertex sooner, but by no guaranteed-short route.

### A note on the timings

Step counts are the honest measure — they are the real number of comparisons or
visits performed, and they do not change between runs. The nanosecond timings do
change, because JVM warm-up, just-in-time compilation and the operating system
scheduler all interfere at this scale. In the table above DFS looks "faster"
than BFS despite identical step counts; that is measurement noise, not a faster
algorithm. The program says so on screen too.

---

## 8. Input Validation and Error Handling

| Situation | System response |
|---|---|
| Menu choice out of range, or not a number | Re-prompts: "Enter a number between 1 and 9." |
| Text entered where a number is required | Re-prompts: "'abc' is not a whole number." |
| Blank input | Re-prompts: "This cannot be left blank." |
| Invalid vertex name | Re-prompts with the accepted character set |
| **Pop from an empty stack** | "STACK UNDERFLOW — the stack is empty, nothing to pop." |
| Peek at an empty stack | Reported, not crashed |
| **Dequeue from an empty queue** | "QUEUE UNDERFLOW — the queue is empty, nothing to dequeue." |
| Search / delete / display on any empty structure | Reported with a hint on how to add data |
| Array or list index out of range | Range is shown in the prompt and enforced |
| Delete a value that is not present | "… is not in the array/list." |
| **Binary search on unsorted data** | Refused, with an offer to sort first — binary search on unsorted data silently returns wrong answers |
| Duplicate vertex | "… already exists. Duplicates are not allowed." |
| Duplicate edge | "An edge between these two vertices already exists." |
| Self-loop | "A vertex cannot be connected to itself." |
| Edge to a non-existent vertex | "One or both vertices do not exist." |
| Remove an edge that does not exist | "There is no direct edge between these two vertices." |
| Remove a vertex that has edges | Warns how many edges go with it, asks to confirm, then removes them cleanly |
| Traversal from an unknown vertex | "… is not in the graph." |
| Traversal of a disconnected graph | Reports how many vertices could not be reached |
| Shortest path with no route | "There is no path between … and …" |
| Performance table with nothing recorded | Explains which menus to use first |

---

## 9. Testing

Two levels, both reproducible from the submitted folder.

1. **Automated component tests** — `java -cp bin app.StructureTest` runs **130
   checks** across all five structures, both search algorithms and the
   performance tracker, including boundary cases: empty pop, empty dequeue,
   queue reuse after draining, invalid indexes, binary search on an empty array,
   a disconnected graph, and a 12-vertex / 66-edge dense graph that exercises the
   DFS stack worst case. **Result: 130/130 pass.**
2. **Scripted end-to-end menu runs** — `java -cp bin app.Main --sample --no-pause
   < docs/test_input_graph.txt` drives every menu and submenu including the
   invalid-input paths, and exits cleanly with no exception.

Full details are in `docs/TEST_PLAN.md`.

---

## 10. GitHub Collaboration

Each member worked on their own feature branch and merged through a pull request
reviewed by another member:

| Branch | Member | Content |
|---|---|---|
| `feature/array-searching` | Ijas | DynamicArray, SearchAlgorithms, menus 1 and 5 |
| `feature/stack-queue` | Isham | IntStack, IntQueue, menus 2 and 3 |
| `feature/linkedlist-performance` | Arshad | SinglyLinkedList, PerformanceTracker, menus 4, 7 and 8 |
| `feature/graph-traversal` | Nafeel | Graph, BFS, DFS, shortest path, menu 6 |
| `feature/integration-docs` | All | MenuUI, InputValidator, Main, tests, documentation |

Step-by-step commands are in `docs/GIT_WORKFLOW.md`.

---

## 11. Deliverables Status

- [x] Array component completed
- [x] Stack component completed
- [x] Queue component completed
- [x] Linked List component completed
- [x] Searching functionality completed (linear and binary)
- [x] **Graph component completed** (adjacency list, add/remove vertex and edge, display)
- [x] **BFS and DFS traversal completed**
- [x] Performance / complexity demonstration completed
- [x] Main console application integrated
- [x] Input validation and empty-structure handling
- [x] All group members' names and student IDs recorded
- [x] Responsibilities and individual contributions documented
- [ ] GitHub repository with branches, commits and pull requests — see `docs/GIT_WORKFLOW.md`
- [ ] One merged demonstration video, under 15 minutes, all faces visible — see `docs/DEMO_VIDEO_SCRIPT.md`
- [ ] Zipped folder uploaded through the LMS link by the group leader
- [ ] If using Google Drive: Editor access to **asanka.r@sltc.ac.lk** and **kaushika.w@sltc.ac.lk**

The unticked items are the ones the group completes outside the code.
`docs/SUBMISSION_CHECKLIST.md` walks through each.
