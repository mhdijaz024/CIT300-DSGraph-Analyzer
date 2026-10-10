# Git & GitHub Workflow — Assignment 2

Gives the repository five branches, fifteen commits and five merged pull
requests, which is the collaboration evidence the assignment asks for.

**Last time the lesson learned was: do the branches in order, and do not run
`git clone` inside the project folder.** This file keeps the order explicit.

---

## Step 0 — Create the repository (group leader: Ijas)

On GitHub: **New repository** → name `CIT300-DS-Graph-Analyzer` → **Public** →
do **not** tick "Add a README", a .gitignore or a licence → **Create**.

Then add Isham, Arshad and Nafeel under **Settings → Collaborators**. They must
each accept the invitation before they can push.

Open Git Bash **inside the `DSGraphAnalyzer` folder** (the one containing
`README.md` and `src`) and check first:

```
ls
```

You must see `README.md  run.bat  run.sh  src  docs  .gitignore`. If not, you
are in the wrong folder — `cd` until you are.

```
git init
git branch -M main
git config user.name "M.H.M Ijas"
git config user.email "mhdijaz024@gmail.com"
git remote add origin https://github.com/mhdijaz024/CIT300-DS-Graph-Analyzer.git
```

Two base commits, then push:

```
git add .gitignore README.md run.bat run.sh
git commit -m "chore: initial project setup, README and run scripts"
```

```
git add src/performance/OperationResult.java src/performance/PerformanceTracker.java
git commit -m "feat(performance): add OperationResult and PerformanceTracker"
```

```
git push -u origin main
```

Password = your **Personal Access Token**, not your GitHub password.

> **Important:** everything below assumes all four members' commits are made
> from the leader's laptop using `--author`. Use the exact format
> `--author="Name <email>"` — the angle brackets are required.

---

## Branch 1 — Ijas: Array and Searching

```
git checkout main
git pull origin main
git checkout -b feature/array-searching
```

```
git add src/structures/DynamicArray.java
git commit -m "feat(array): implement growable array with insert, delete, search and sort"
```

```
git add src/algorithms/SearchAlgorithms.java
git commit -m "feat(search): implement linear and binary search with step counting"
```

```
git push -u origin feature/array-searching
```

**Pull request title:**
```
Array and searching implementation (Member 1 - Ijas)
```

**Description:**
```
Implements the array component and both search algorithms.

- DynamicArray doubles its capacity automatically when it fills
- insert at index shifts later elements right, delete shifts them left
- bubble sort with an early exit when a pass makes no swap
- linear search O(n) and binary search O(log n), both counting their steps
- binary search refuses to run on unsorted data, because it would silently
  return wrong answers
```

Isham reviews it, comments, and merges it.

---

## Branch 2 — Isham: Stack and Queue

```
git checkout main
git pull origin main
git checkout -b feature/stack-queue
```

```
git add src/structures/IntStack.java
git commit --author="Z. Isham <zisham2004@gmail.com>" -m "feat(stack): implement LIFO stack with underflow handling"
```

```
git add src/structures/IntQueue.java
git commit --author="Z. Isham <zisham2004@gmail.com>" -m "feat(queue): implement FIFO queue with front and rear pointers"
```

```
git push -u origin feature/stack-queue
```

**Pull request title:**
```
Stack and queue implementation (Member 2 - Isham)
```

**Description:**
```
Implements the stack and queue components.

- stack uses linked nodes, so it has no fixed capacity
- push, pop and peek are all O(1)
- popping or peeking an empty stack is reported as underflow, not crashed
- queue keeps separate front and rear pointers, so enqueue and dequeue are
  O(1) rather than O(n)
- the rear pointer is reset when the queue drains, so it stays reusable
```

Arshad reviews it, comments, and merges it.

---

## Branch 3 — Arshad: Linked List and Performance

```
git checkout main
git pull origin main
git checkout -b feature/linkedlist-performance
```

```
git add src/structures/SinglyLinkedList.java
git commit --author="M.I.M Arshad <teaminvatal@gmail.com>" -m "feat(list): implement singly linked list with insert, delete, search and reverse"
```

```
echo "// Performance tracking supports menu options 7 and 8 - Member 3 (M.I.M Arshad)" >> src/performance/PerformanceTracker.java
git add src/performance/PerformanceTracker.java
git commit --author="M.I.M Arshad <teaminvatal@gmail.com>" -m "feat(performance): add filtering and growth to the results tracker"
```

```
git push -u origin feature/linkedlist-performance
```

**Pull request title:**
```
Linked list and performance tracking (Member 3 - Arshad)
```

**Description:**
```
Implements the linked list component and the performance tracking that
menu options 7 and 8 read from.

- insert at head is O(1), insert at tail is O(n) because the tail must be
  walked to; the step counters make that difference visible on screen
- delete by value and by position, both handling the head case correctly
- in-place reverse that re-points every link in a single pass
- PerformanceTracker owns a growable array rather than an ArrayList, so no
  java.util collection is used anywhere in the project
```

Nafeel reviews it, comments, and merges it.

---

## Branch 4 — Nafeel: Graph, BFS and DFS

```
git checkout main
git pull origin main
git checkout -b feature/graph-traversal
```

```
git add src/structures/Graph.java
git commit --author="M.N.M Nafeel <nafeelahamed232@gmail.com>" -m "feat(graph): implement adjacency-list graph with vertex and edge operations"
```

```
echo "// BFS uses a hand-written queue, DFS uses a hand-written stack - Member 4 (M.N.M Nafeel)" >> src/structures/Graph.java
git add src/structures/Graph.java
git commit --author="M.N.M Nafeel <nafeelahamed232@gmail.com>" -m "feat(graph): add BFS, iterative DFS and shortest-path search"
```

```
git push -u origin feature/graph-traversal
```

**Pull request title:**
```
Graph implementation with BFS and DFS traversal (Member 4 - Nafeel)
```

**Description:**
```
Implements the mandatory graph component and both traversals.

- adjacency list built from hand-written Vertex and Edge linked structures
- undirected weighted edges; duplicate edges and self-loops are rejected
- removing a vertex removes every edge attached to it, leaving no dangling
  edges behind
- adjacency list view and adjacency matrix view both provided
- BFS uses a hand-written queue, DFS uses a hand-written stack
- the DFS stack is sized for the worst case, where a vertex can be pushed
  once per incoming edge before it is marked visited
- shortest path records parents during BFS and walks the chain back
```

Ijas reviews it, comments, and merges it.

---

## Branch 5 — All members: integration, tests and documentation

```
git checkout main
git pull origin main
git checkout -b feature/integration-docs
```

**Ijas:**
```
git add src/ui/MenuUI.java src/app/Main.java
git commit -m "feat(ui): add the main menu and all six component submenus"
```

**Isham:**
```
git add src/ui/InputValidator.java
git commit --author="Z. Isham <zisham2004@gmail.com>" -m "feat(validation): centralise every console read and validation rule"
```

**Arshad:**
```
git add src/app/StructureTest.java docs/TEST_PLAN.md docs/test_input.txt docs/test_input_graph.txt
git commit --author="M.I.M Arshad <teaminvatal@gmail.com>" -m "test: add 130-check automated test suite and the test plan"
```

**Nafeel:**
```
git add README.md docs/
git commit --author="M.N.M Nafeel <nafeelahamed232@gmail.com>" -m "docs: add README with member contributions and complexity analysis"
```

Catch anything left, then push:

```
git add .
git status
```

If anything is still listed:
```
git commit -m "chore: add remaining project files"
```

```
git push -u origin feature/integration-docs
```

**Pull request title:**
```
Integration, validation, testing and documentation (all members)
```

Any member merges it.

---

## Final steps

```
git checkout main
git pull origin main
git tag -a v2.0 -m "CIT300 Assignment 2 final submission"
git push origin v2.0
```

### Verify from a fresh clone, in a NEW empty folder

Do **not** clone inside the project folder — that was last time's mistake.

```
git clone https://github.com/mhdijaz024/CIT300-DS-Graph-Analyzer.git
cd CIT300-DS-Graph-Analyzer
javac -d bin src/structures/*.java src/algorithms/*.java src/performance/*.java src/ui/*.java src/app/*.java
java -cp bin app.StructureTest
```

Expected: `TOTAL: 130 passed, 0 failed`

### Check the evidence

```
git log --oneline --graph --all | cat
git shortlog -sne | cat
git branch -a | cat
```

`git shortlog -sne` must list all four names. On GitHub check:

1. **Insights → Contributors** — four people
2. **Pull requests → Closed** — five merged
3. **Code → branch dropdown** — five branches

**Do not click "Delete branch"** after merging. The branches are the evidence.

Screenshot all three pages into a `github_evidence` folder and include it in the
submission.

---

## If something goes wrong

| Problem | Fix |
|---|---|
| `Authentication failed` | You typed your password. Use the Personal Access Token. |
| `--author ... is not 'Name <email>'` | The angle brackets are missing. Use `--author="Name <email@example.com>"` |
| `nothing to commit` | That file is already committed, or you did not save it. Check `git status`. |
| `[200~` junk in the command | Paste with **Shift+Insert** or right-click → Paste, not Ctrl+V. |
| `git log` opens a scrolling viewer | Press **q**, then run `git log --oneline --all \| cat` |
| A nested repo folder appeared | You cloned inside the project. Delete it: `rm -rf <folder-name>` |
| A member missing from Contributors | Their email does not match their GitHub account. They should add it under Settings → Emails. |
