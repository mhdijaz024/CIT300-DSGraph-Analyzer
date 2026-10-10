# Demonstration Video Script — target 13 minutes (limit 15)

**Non-negotiables from the assignment:**
- ONE merged video, **under 15 minutes**
- **Every member's face clearly visible throughout their own section** — a
  recording showing only the screen, the code, the output or a voice without the
  face is explicitly stated to be **not sufficient**
- Each member must follow the same six steps: introduce themselves → state their
  responsibility → show their code → run their functionality → explain the
  data structure, algorithm and complexity → show how it integrates

**Before recording:**
```bash
java -cp bin app.Main --sample
```
Terminal font 16pt or larger, full screen, record at 1080p, webcam overlay on
and not covered by the terminal.

---

## Section 0 — Introduction (Ijas) — 0:45

All four members on camera together.

> "This is our CIT300 Assignment 2, the Data Structure and Graph Performance
> Analyzer. It is a single integrated Java console application. Every data
> structure in it is written from scratch — we do not use a single java.util
> collection anywhere in the project."

Each member says one line to camera:

- "M.H.M Ijas, 23DA2-0675, group leader. Array and searching."
- "Z. Isham, 23DA2-0677. Stack and queue."
- "M.I.M Arshad, 23DA2-0634. Linked list and the performance tracking."
- "M.N.M Nafeel, 23DA2-0678. Graph, BFS and DFS."

Show the README member table on screen for about 8 seconds.

---

## Section 1 — Ijas: Array and Searching — 3:00

### Introduce and show the code (1:00)

Open `src/structures/DynamicArray.java`.

> "I built the array component. It is a growable array on a plain Java int
> array. When it fills up, `grow()` doubles the capacity and copies across.
> Insert at an index shifts everything after it one place right, which is why
> that operation is O(n) while insert at the end is O(1)."

Open `src/algorithms/SearchAlgorithms.java`.

> "I also built both search algorithms. The important part is that each one
> counts its own steps, so we can compare them honestly rather than just
> quoting the textbook."

### Demonstrate (2:00)

| Menu path | Input | What to say on camera |
|---|---|---|
| 1 → 8 | — | "Sample data loaded. Fifteen values, unsorted." |
| 1 → 1 | `55` | "Insert at the end — one step, O(1)." |
| 1 → 2 | index `0`, value `99` | "Insert at index 0 — notice it reports shifting 16 elements. That is the O(n)." |
| 1 → 5 | `90` | "Array search is a linear scan. It reports the comparison count." |
| 1 → 5 | `404` | "A missing value costs the full n comparisons — that is the worst case." |
| 1 → 9, then 5 → 4 | — | "Now fifty values for the searching demonstration." |
| 5 → 2 | — | "Binary search on unsorted data — and it refuses. This matters: binary search on unsorted data does not crash, it silently returns the wrong answer. So we block it." |
| — | `Y` | "We let it sort first. Bubble sort, O(n²)." |
| 5 → 3 | `250` | **The key moment.** "Both algorithms, same data, same target." |

Point at the table on screen:

> "Linear search: fifty steps. Binary search: six. Same answer, same data. Linear
> checks every element, so it is O(n). Binary halves the remaining range on every
> comparison, so it is O(log n) — about six comparisons for fifty values. At a
> million values it would be twenty against a million.
>
> But look at the trade-off the program prints: binary search needs the data
> sorted, and our bubble sort is O(n²). For one search on unsorted data, linear
> is actually the better choice. For many repeated searches, you sort once and
> binary search wins easily."

---

## Section 2 — Isham: Stack and Queue — 2:30

### Show the code (0:50)

Open `IntStack.java` and `IntQueue.java` side by side.

> "I built the stack and the queue. Both use linked nodes rather than arrays, so
> neither has a fixed capacity. The stack only ever touches the top node, so
> push, pop and peek are all O(1). The queue keeps two pointers, front and rear
> — that is the detail that matters, because without a rear pointer you would
> have to walk the whole list on every enqueue and it would be O(n)."

Scroll to the underflow handling.

> "And both handle the empty case. Popping an empty stack throws a controlled
> exception that the menu reports as underflow, instead of crashing."

### Demonstrate (1:40)

| Menu path | Input | What to say |
|---|---|---|
| 2 → 1 | `10`, then `20`, `30` | "Three pushes." |
| 2 → 5 | — | "Display — 30 is on top, because the last one in is the first one out." |
| 2 → 3 | — | "Peek reads the top without removing it. Still three items." |
| 2 → 2 | — | "Pop returns 30 — LIFO." |
| 2 → 2, 2 → 2, 2 → 2 | — | "Keep popping until it is empty, then one more." |
| — | — | **"STACK UNDERFLOW. Handled, not crashed."** |
| 3 → 1 | `10`, `20`, `30` | "Now the queue, same three values." |
| 3 → 5 | — | "Front to rear. 10 is at the front." |
| 3 → 2 | — | "Dequeue returns 10 — FIFO. Opposite of the stack, same input." |
| 3 → 2 ×3 | — | **"QUEUE UNDERFLOW."** |
| 3 → 1 | `99` | "And it is still reusable — the rear pointer resets when the queue drains. That is a bug a lot of implementations have." |

> "That LIFO versus FIFO difference is not just a detail — it is exactly what
> makes BFS and DFS behave differently, which Nafeel will show."

---

## Section 3 — Arshad: Linked List and Performance — 2:45

### Show the code (0:50)

Open `SinglyLinkedList.java`.

> "I built the linked list. Each node holds a value and a pointer to the next
> one. Insert at the head is O(1) — you just point the new node at the old head.
> Insert at the tail is O(n), because you have to walk the whole list to find
> the end. The step counters make that visible on screen."

Open `PerformanceTracker.java`.

> "I also built the performance tracking that menu 7 and 8 use. It stores every
> measurement — operation, algorithm, steps, time, complexity. It uses its own
> growable array rather than an ArrayList, so the no-collections rule holds
> across the whole project."

### Demonstrate (1:55)

| Menu path | Input | What to say |
|---|---|---|
| 4 → 1 | `10`, `20`, `30` | "Three head inserts — each reports one step." |
| 4 → 8 | — | "HEAD → 30 → 20 → 10 → null. Reversed from the input order, because each new one goes at the front." |
| 4 → 2 | `99` | "Tail insert. Notice: three steps, not one. It had to walk the list." |
| 4 → 3 | position `2`, value `55` | "Insert at a position." |
| 4 → 6 | `55` | "Search — found at position 2 after 3 steps." |
| 4 → 6 | `404` | "Missing value costs the full traversal." |
| 4 → 7 | — | "Reverse — one pass, re-pointing every link. O(n), no extra memory." |
| 4 → 4 | `99` | "Delete by value." |
| 4 → 9, then 8 | — | "And this is what I built the tracker for." |

Point at the Display All Results table:

> "Every operation we have run this session, with its real step count. Look at
> the two linked-list inserts: head insert one step, tail insert three. Same
> structure, same data, different complexity — O(1) against O(n). That is the
> whole point of the module in one table."

---

## Section 4 — Nafeel: Graph, BFS and DFS — 3:00

### Show the code (1:00)

Open `Graph.java`, scroll to the `Vertex` and `Edge` inner classes.

> "I built the graph. It is an adjacency list — every vertex holds a linked list
> of its edges. I chose that over a matrix because a matrix always uses V-squared
> memory even when the graph is sparse, while an adjacency list uses V plus E.
> The matrix view is still in the program, in option 5, because it is useful to
> see."

Scroll to `bfs()` and `dfs()`.

> "BFS uses a queue, DFS uses a stack — the same structures Isham built. And
> notice this comment on the DFS stack size. We found a real bug in testing: the
> stack was sized to the number of vertices, but a vertex can be pushed once per
> incoming edge before it is marked visited. On a dense graph that overflowed the
> array. We sized it for the worst case and added a twelve-vertex, sixty-six-edge
> regression test."

### Demonstrate (2:00)

| Menu path | Input | What to say |
|---|---|---|
| 6 → 10 | — | "Sample graph: eight vertices, nine edges. Vertex H has no edges on purpose." |
| 6 → 5 | — | "Adjacency list, then the same graph as a matrix. Look how many zeros there are — that is why the list is the better store for a sparse graph." |
| 6 → 1 | `TEST` | "Add a vertex." |
| 6 → 1 | `TEST` again | "Duplicate rejected." |
| 6 → 2 | `A`, `A`, `5` | "Self-loop rejected." |
| 6 → 2 | `A`, `Nowhere`, `5` | "Edge to a vertex that does not exist — rejected." |
| 6 → 6 | `A` | "BFS from A." |
| 6 → 7 | `A` | "DFS from A." |
| 6 → 8 | `A` | **The key moment.** "Both, side by side." |

Point at the comparison:

> "Same graph, same start, twenty-five steps each. Both are O(V plus E) — each
> visits every reachable vertex once and looks at each of its edges once.
>
> What differs is the order, and it comes straight from the structure. BFS holds
> pending vertices in a queue, so the oldest goes first and it spreads outward
> level by level. DFS uses a stack, so the newest goes first and it dives deep
> before backtracking.
>
> That is why BFS finds the shortest path by edge count and DFS does not."

| Menu path | Input | What to say |
|---|---|---|
| 6 → 9 | `A`, `G` | "Shortest path — three edges, found by BFS tracking parents." |
| 6 → 9 | `A`, `H` | "And no path to H, because it has no edges. The graph is not fully connected and the program says so." |
| 6 → 3 | `TEST` | "Removing a vertex also removes every edge attached to it — no dangling edges." |

---

## Section 5 — Integration and testing (Ijas narrates, all on camera) — 1:30

> "The components are genuinely integrated, not just four programs in one menu.
> The searching module runs on the array module's own data — one data set, two
> algorithms. And every component feeds the same performance tracker, which is
> what options 7 and 8 read from."

Show menu 7:

> "The comparison table the assignment asks for: linear against binary, BFS
> against DFS, with steps, time and complexity."

Then:

> "One honest note. The step counts are the real measure — they never change
> between runs. The nanosecond timings do, because JVM warm-up and the operating
> system interfere at this scale. In this run DFS looks faster than BFS despite
> identical step counts. That is noise, not a faster algorithm, and the program
> says so on screen."

Run the test suite live:

```bash
java -cp bin app.StructureTest
```

> "One hundred and thirty automated checks across all five structures, both
> search algorithms and the tracker. All passing. These include the boundary
> cases — popping an empty stack, dequeuing an empty queue, binary search on an
> empty array, a disconnected graph, and the dense graph that caught our DFS bug."

---

## Section 6 — GitHub and close (Ijas) — 0:45

On screen:
- **Insights → Contributors** — four contributors
- **Pull requests → Closed** — five merged pull requests
- Terminal: `git log --oneline --graph --all | cat`

> "Each member worked on their own branch and merged through a pull request
> reviewed by someone else."

All four on camera:

> "That is the complete system: array, stack, queue, linked list, linear and
> binary search, a graph with BFS and DFS, and a performance comparison showing
> why they differ. Thank you."

---

## Recording checklist

- [ ] Every member's webcam on and face clearly visible during their own section
- [ ] Each member states name, student ID and assigned responsibility on camera
- [ ] Each member shows their code **and** explains it, not just runs it
- [ ] Each member explains complexity, not only what the code does
- [ ] At least one invalid input demonstrated per section
- [ ] The two comparison screens (menu 5 → 3, and menu 6 → 8) are both shown
- [ ] Menu 7 performance table shown
- [ ] Test suite run live
- [ ] GitHub contributors and pull request pages shown
- [ ] Terminal font large enough to read at 1080p
- [ ] Audio audible in every section — check the quiet speakers before merging
- [ ] Final merged video is **under 15 minutes**
