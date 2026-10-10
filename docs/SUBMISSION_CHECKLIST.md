# Final Submission Checklist — Assignment 2

**Deadline: Tuesday 13 October 2026, 11:59 PM** (from the LMS).

> Note: section 17 of the assignment PDF says "29th September". That is a
> copy-paste error carried over from Assignment 1 — the LMS entry for
> *CIT300 Graded Practical Assignment 2 (10%)* shows **Opened: 2 October,
> Due: 13 October 11:59 PM**. Go by the LMS. If anyone in the group is unsure,
> ask the lecturer rather than guessing.

**The LMS says: "Only the group leader is required to upload the zipped folder
to the provided link."** So Ijas submits, once, as a ZIP.

---

## 1. Code and documentation — already done

- [x] Array component completed
- [x] Stack component completed (with underflow handling)
- [x] Queue component completed (with underflow handling)
- [x] Linked List component completed
- [x] Searching completed — linear **and** binary
- [x] **Graph component completed** — adjacency list, add/remove vertex and edge, display
- [x] **BFS and DFS traversal completed**
- [x] Performance / complexity demonstration completed
- [x] Main console application integrated with submenus
- [x] Input validation and empty-structure handling throughout
- [x] README with project title, description, members, IDs, responsibilities, individual contributions, technologies, features and run instructions
- [x] Automated test suite included and passing (130/130)

---

## 2. Check your own details first

Open `README.md` section 2 and check every character against your student ID
cards:

| Name | Student ID | Checked |
|---|---|---|
| M.H.M Ijas (Leader) | 23DA2-0675 | ☐ |
| Z. Isham | 23DA2-0677 | ☐ |
| M.I.M Arshad | 23DA2-0634 | ☐ |
| M.N.M Nafeel | 23DA2-0678 | ☐ |

The assignment states plainly that missing, incomplete or incorrect member
information may lose marks.

The same four names also appear in:
- `src/ui/MenuUI.java` → `printBanner()` and every submenu header
- `src/app/StructureTest.java` → the section titles

Update all three places if anything is wrong.

---

## 3. GitHub repository

- [ ] Repository created and **Public**
- [ ] All four members appear under **Insights → Contributors**
- [ ] Five branches exist: `feature/array-searching`, `feature/stack-queue`,
      `feature/linkedlist-performance`, `feature/graph-traversal`,
      `feature/integration-docs`
- [ ] Five pull requests opened, reviewed by another member, and merged
- [ ] **Branches NOT deleted** after merging — they are the evidence
- [ ] `v2.0` tag pushed
- [ ] Screenshots taken of Contributors, Pull requests and `git log --graph`,
      saved into a `github_evidence` folder

Commands are in `GIT_WORKFLOW.md`.

---

## 4. Demonstration video

- [ ] ONE merged video, **under 15 minutes**
- [ ] Every member's face clearly visible throughout their own section
- [ ] Each member: introduces themselves (name, ID, responsibility) → explains
      their contribution → **shows their code** → runs their functionality →
      explains the data structure, algorithm and complexity → shows integration
- [ ] Audio audible in every section
- [ ] Added to the submission folder, or its link included

Script: `DEMO_VIDEO_SCRIPT.md`.

---

## 5. Build the ZIP

From a **clean copy**, delete the `bin` folder first so the ZIP contains source
only:

```bash
rm -rf bin
```

Then zip the whole `DSGraphAnalyzer` folder as:

```
CIT300_Assignment2_Group<X>_DSGraphAnalyzer.zip
```

The ZIP must contain:
- [ ] `src/` with all 11 Java files
- [ ] `README.md`
- [ ] `docs/` with all five documents
- [ ] `run.bat` and `run.sh`
- [ ] `github_evidence/` screenshots
- [ ] The demonstration video, or a text file with its link

---

## 6. Sanity check before you submit

Unzip into a **new empty folder** on a different machine if possible, then:

```bash
javac -d bin src/structures/*.java src/algorithms/*.java src/performance/*.java src/ui/*.java src/app/*.java
java -cp bin app.StructureTest
java -cp bin app.Main
```

- [ ] It compiles with no error
- [ ] The test suite prints **130 passed, 0 failed**
- [ ] The program runs and the menu appears
- [ ] Menu 5 → 3 shows the linear vs binary comparison
- [ ] Menu 6 → 8 shows the BFS vs DFS comparison
- [ ] Menu 7 shows the performance table

If it works on a machine that has never seen the project, it will work on the
marker's machine.

---

## 7. Submit

- [ ] **Ijas only** uploads the ZIP through the designated LMS link
- [ ] LMS shows the submission as received
- [ ] Screenshot of the LMS confirmation taken
- [ ] Submitted on or before **13 October, 11:59 PM**

---

## 8. Only if the ZIP is too large for the LMS

The PDF allows a Google Drive fallback. If you use it:

1. Upload the complete project to a Google Drive folder
2. Copy the **folder** link into a Notepad (.txt) file
3. Upload the .txt through the LMS link
4. Share the folder with **Editor** access to:
   - [ ] `asanka.r@sltc.ac.lk`
   - [ ] `kaushika.w@sltc.ac.lk`
5. Reopen the Share dialog and **verify both say Editor**, not Viewer
6. Open the link in an incognito window to confirm it is not broken

> Failure to provide Editor permissions before submission may result in
> **0 marks**, and the assignment states you are responsible for verifying them.

---

## 9. Things that lost marks or caused trouble last time

- A `git clone` run inside the project folder, creating a nested duplicate —
  always clone into a **new empty folder**
- A pull request merged with "No description provided" — paste the description
- Branches created out of order, so files were committed to the wrong branch —
  follow `GIT_WORKFLOW.md` top to bottom
- Pasting into Git Bash with Ctrl+V, which inserts `[200~` junk — use
  **Shift+Insert** or right-click → Paste
