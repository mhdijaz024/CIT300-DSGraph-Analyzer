# Test Plan and Results — Assignment 2

**Project:** Data Structure and Graph Performance Analyzer
**Module:** CIT300 – Data Structures and Algorithms, Graded Practical Assignment 2
**Tested on:** JDK 21 (compiles on JDK 8+)

---

## A. Automated component tests

```bash
javac -d bin src/structures/*.java src/algorithms/*.java src/performance/*.java src/ui/*.java src/app/*.java
java -cp bin app.StructureTest
```

**Result: 130 checks, 130 passed, 0 failed.**

| Group | Checks | What is covered |
|---|---|---|
| 1. Array | 21 | empty-array search and delete, capacity growth past the initial size, insert at index with shifting, invalid index rejected, delete by value and index, sorted/unsorted detection, bubble sort correctness, early exit on sorted data, out-of-range delete throws |
| 2. Stack | 14 | empty stack, **pop underflow rejected**, **peek on empty rejected**, LIFO order, peek does not remove, search by depth, reuse after emptying |
| 3. Queue | 10 | empty queue, **dequeue underflow rejected**, FIFO order, peek returns earliest arrival, search by position, **reuse after draining (rear pointer reset)** |
| 4. Linked List | 21 | empty-list search and delete, head insert is one step, tail insert walks the list, insert at position, invalid position rejected, delete head and middle, reverse correctness, out-of-range delete throws |
| 5. Searching | 15 | linear finds the last element in exactly n steps, binary finds the same element in ≤ 8 steps, binary beats linear on the worst case, both report misses correctly, binary terminates on a miss, first-element edge case, isSorted accepts and rejects correctly, **binary search on an empty array is safe** |
| 6. Graph | 35 | empty graph, duplicate vertex, duplicate edge, self-loop, edge to a missing vertex, undirected edges, BFS order and step counting, DFS visits each vertex once, unknown start vertex, **isolated vertex not reached**, shortest path found, **no path to an isolated vertex**, edge removal, vertex removal with edge cleanup, **12-vertex / 66-edge dense graph for the DFS stack worst case**, BFS and DFS cost the same order of steps |
| 7. Performance Tracker | 14 | empty tracker, recording, filter by operation, case-insensitive filter, latestFor hit and miss, stored values correct, row formatting, **growth past the initial capacity of 8**, clear |

---

## B. Scripted end-to-end menu runs

```bash
java -cp bin app.Main --empty --no-pause  < docs/test_input.txt
java -cp bin app.Main --sample --no-pause < docs/test_input_graph.txt
```

Both complete with **no exception** and the second exits cleanly through menu 9.

| # | Test case | Expected | Result |
|---|---|---|---|
| 1 | Text typed where a number is required (`abc`) | "'abc' is not a whole number", re-prompt | Pass |
| 2 | Menu choice out of range | "Enter a number between 1 and 9", re-prompt | Pass |
| 3 | Array insert, insert at index, delete, search | Values shift correctly, step counts reported | Pass |
| 4 | Array search for a missing value | "[NOT FOUND]" with the comparison count | Pass |
| 5 | Array operations on an empty array | Blocked with a hint, no crash | Pass |
| 6 | Array sort | Sorted, comparison count reported | Pass |
| 7 | **Pop an empty stack** | "STACK UNDERFLOW — the stack is empty" | Pass |
| 8 | Push, peek, pop, search on the stack | LIFO order correct | Pass |
| 9 | **Dequeue an empty queue** | "QUEUE UNDERFLOW — the queue is empty" | Pass |
| 10 | Enqueue, peek, dequeue, search on the queue | FIFO order correct | Pass |
| 11 | Linked list insert head / tail / position | Correct placement, step counts differ as expected | Pass |
| 12 | Linked list search found and not found | "Found at position 1 after 2 step(s)" / "not in the list (3 steps)" | Pass |
| 13 | Linked list reverse | Order flipped, size unchanged | Pass |
| 14 | **Binary search on unsorted data** | Refused, offers to sort first; declining cancels safely | Pass |
| 15 | Binary search after agreeing to sort | Runs correctly on the sorted data | Pass |
| 16 | **Compare linear vs binary on 50 values** | Linear 50 steps, binary 6 steps, with the explanation printed | Pass |
| 17 | Graph display | Adjacency list **and** adjacency matrix printed | Pass |
| 18 | BFS from vertex A | `A → C → B → F → D → E → G`, 25 steps | Pass |
| 19 | DFS from vertex A | `A → B → D → C → F → E → G`, 25 steps | Pass |
| 20 | **Disconnected graph** | "1 vertex cannot be reached from A" (vertex H) | Pass |
| 21 | Compare BFS and DFS | Both 25 steps, table plus the order explanation | Pass |
| 22 | Shortest path A → G | 3 edges, correct route | Pass |
| 23 | Shortest path A → H (isolated) | "There is no path between A and H" | Pass |
| 24 | Duplicate vertex | "already exists. Duplicates are not allowed" | Pass |
| 25 | Duplicate edge | "An edge between these two vertices already exists" | Pass |
| 26 | Self-loop | "A vertex cannot be connected to itself" | Pass |
| 27 | Edge to a non-existent vertex | "One or both vertices do not exist" | Pass |
| 28 | Remove an edge | Removed from both adjacency lists | Pass |
| 29 | Remove a vertex | Removed with all its edges | Pass |
| 30 | Performance comparison table | All searches and traversals listed with steps, time and complexity | Pass |
| 31 | Display all results | Every recorded operation plus the live state of all five structures | Pass |
| 32 | Exit confirmation | Session summary printed, clean shutdown | Pass |

---

## C. Defects found and fixed during testing

| Defect | Where | Cause | Fix |
|---|---|---|---|
| A test asserted an array was unsorted when it had become sorted by chance | `StructureTest.testArray()` | After the earlier deletions the array happened to be `[5, 10, 20]`, which is already in order, so the assertion was wrong — the code was correct | Inserted a smaller value first so the array is genuinely unsorted before the check |
| Potential `ArrayIndexOutOfBoundsException` in DFS on a dense graph | `Graph.dfs()` | The stack array was sized to the vertex count, but iterative DFS can push a vertex once per incoming edge before it is marked visited | Sized the stack as `vertexCount + 2 × edgeCount + 1`, and added a 12-vertex / 66-edge dense-graph regression test |

The second one is worth mentioning in the video: it is a real bug that only a
dense graph exposes, and the sparse sample graph would never have caught it.

---

## D. Boundary conditions verified

- Every operation attempted on every empty structure
- Popping and dequeuing until empty, then reusing the structure
- Deleting the first, a middle and the last element of the array and the list
- Array growth across the capacity-doubling threshold
- Performance tracker growth past its initial capacity of 8
- Binary search on an empty array, on the first element and on the last element
- Binary search refused on unsorted data
- A graph that is not fully connected
- A vertex with no edges, and a vertex with several edges being removed
- A fully connected 12-vertex graph (66 edges) for the DFS stack worst case
