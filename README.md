# A1 - Lists and Nodes (CS 2341, Fall 2025)

* **Points:** Q1 (20) + Q2 (40) + Q3 (40) = **100**
* **Due:** Thu, **Sep 4, 2025**, 11:59 PM (America/Chicago)
* **Late policy:** 1 days: −10%; 2 days: −20%; 3 days: −30%; >10 days: no credit.


---

## Assignment Focus

Implement fundamental data structures **from scratch**:

* **Q1:** Linked List (remove duplicates)
* **Q2:** Stack (resizing array, characters only)
* **Q3:** Queue (linked list, integers only)

**Critical requirements**

* **No templates/generics** — each container is type-specific.
* **Custom Node classes** — `IntNode` for integer containers; for Q2 use `char[]` (or optionally a `CharNode` you design).
* **Custom Container classes** — one per problem as needed.
* Each question’s node/container is **tailored to that problem**.

---

## Global Rules

* **Allowed library:** Only Princeton **`algs4`** I/O (`StdIn`, `StdOut`).
* **Forbidden:** All `java.util.*` collections (`ArrayList`, `LinkedList`, `Stack`, `Queue`, etc.).
* **Required filenames (one public class per file):**

    * Q1: `RemoveDuplicates.java`
    * Q2: `Parentheses.java`
    * Q3: `Josephus.java`

---

## Project Layout & Rules

```text
a1-lists-and-nodes/
├─ gradlew / gradlew.bat / build.gradle     (provided; do not edit)
├─ src
│  └─ main
│     └─ java
│        └─ com
│           └─ student_word
│              ├─ RemoveDuplicates.java     (you add)
│              ├─ Parentheses.java          (you add)
│              └─ Josephus.java             (you add)
└─ (other project files)                    (do not edit)
```

* **Only add code** under `src/main/java/com/student_word/`.
* **Do not** modify Gradle files, tests, or anything outside `src/main/java/com/student_word/`.
* Your classes must be named exactly:

    * `RemoveDuplicates`
    * `Parentheses`
    * `Josephus`
* Your code **must compile** with the provided Gradle wrapper.
* Follow the assignment’s **I/O contract** exactly as described so tests can run.

---

## Build & Run

### Using Gradle (recommended)

```bash
# macOS/Linux/Windows (PowerShell/CMD)
./gradlew clean test     # compiles and runs tests
./gradlew build          # builds (and tests) the project
```

If you want to run a class manually (outside tests), run it with `java` using the compiled classes:

```bash
# macOS/Linux
java -cp build/classes/java/main:algs4.jar com.student_word.ClassName < input.txt
java -cp build/classes/java/main:algs4.jar com.student_word.ClassName arg1 arg2

# Windows
java -cp build\classes\java\main;algs4.jar com.student_word.ClassName < input.txt
java -cp build\classes\java\main;algs4.jar com.student_word.ClassName arg1 arg2
```

> If your Gradle build already declares `algs4` as a dependency, you can omit `algs4.jar` from the `-cp` above.

### Plain `javac` (fallback)

```bash
# macOS/Linux
javac -cp .:algs4.jar -d out src/main/java/com/student_word/*.java
java  -cp out:algs4.jar com.student_word.ClassName

# Windows
javac -cp .;algs4.jar -d out src\main\java\com\student_word\*.java
java  -cp out;algs4.jar com.student_word.ClassName
```

---

## Q1 — Delete Duplicate-Value Nodes from a Sorted Linked List (20 pts)

**File:** `RemoveDuplicates.java`
**Input:** `StdIn`
**Container:** Integer-only **singly linked list**
**Node:** `IntNode` (you design it)

### Context

Given the head of a **sorted** integer linked list (ascending), remove duplicates so each value appears exactly once.

### Example

Input: `1 -> 2 -> 2 -> 3 -> 3 -> 3 -> 3 -> null`
Output: `1 -> 2 -> 3 -> null`

### Function Description

Implement a method that **removes duplicates** from a sorted list and returns the (possibly new) head.

* **Function:** `IntNode removeDuplicates(IntNode head)`
* **Returns:** head of the de-duplicated list

### Input Format

```
t            # number of test cases
n            # number of elements
value_1
value_2
...
value_n
```

### Constraints

* 1 ≤ t ≤ 10
* 0 ≤ n ≤ 1000
* −10^9 ≤ value ≤ 10^9

### Sample Input

```
1
5
1
2
2
3
4
```

### Sample Output

```
1 2 3 4
```

### Starter Code

```java
package com.student_word;

// You will design the fields/constructors.
class IntNode {
    // e.g., data, next
}

class IntLinkedList {
    // e.g., private head;

    void append(int value) {
        throw new UnsupportedOperationException();
    }

    IntNode removeDuplicates(IntNode head) {
        throw new UnsupportedOperationException();
    }

    void print(IntNode head) {
        throw new UnsupportedOperationException();
    }
}

public class RemoveDuplicates {
    public static void main(String[] args) {
        // Read t, then for each test: read n and n values; build list; call removeDuplicates; print.
        throw new UnsupportedOperationException();
    }
}
```

---

## Q2 — Parentheses Balance Checker (40 pts)

**File:** `Parentheses.java`
**Input:** `StdIn` (entire input is the string to check)
**Container:** Character-only **resizing array stack** (start capacity = 1, **double when full**)
**Storage:** `char[]` (or optionally your own `CharNode[]` design)

### Context

Determine whether brackets in the input are balanced and properly nested. Consider only `()[]{}`; ignore all other characters.

### Examples

* `[()]{}{[()()]()}` → `true`
* `[(])` → `false`

### Function Description

* **Function:** `static boolean isBalanced(String s)`
* **Returns:** `true` if balanced; otherwise `false`

### Constraints

* 0 ≤ input length ≤ 100,000
* Stack **must** start with capacity 1 and **double** when full (optional shrink-on-quarter-full is allowed)

### Sample Input

```
[()]{}{[()()]()}
```

### Sample Output

```
true
```

### Starter Code (signatures only — no implementation)

```java
package com.student_word;

// Implement a character stack backed by a resizing char[].
class ResizingCharStack {
    ResizingCharStack() {
        throw new UnsupportedOperationException();
    }

    boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    void push(char c) {
        throw new UnsupportedOperationException();
    }

    char pop() {
        throw new UnsupportedOperationException();
    }
}

// Optional alternative you design yourself:
// class CharNode { /* ... */ }

public class Parentheses {
    static boolean isBalanced(String s) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        // Read entire StdIn as one string; print true/false using StdOut.
        throw new UnsupportedOperationException();
    }
}
```

---

## Q3 — Josephus Problem (40 pts)

**File:** `Josephus.java`
**Input:** **Command-line args**: `N M`
**Container:** Integer-only **linked-list queue**
**Node:** `IntNode` (you may reuse your Q1 design or create a new one)

### Context

`N` people labeled `0..N-1` sit in a circle. Repeatedly remove every `M`-th person and print the **elimination order**.

### Example

`N = 7, M = 2` → order: `1 3 5 0 4 2 6`

### Function Description

* **Function:** `static void printJosephusOrder(int N, int M)`
* **Behavior:** prints the elimination order (space-separated) to `StdOut`.

### Input Format

```bash
# macOS/Linux
java -cp build/classes/java/main:algs4.jar com.student_word.Josephus 7 2

# Windows
java -cp build\classes\java\main;algs4.jar com.student_word.Josephus 7 2
```

### Constraints

* 1 ≤ N ≤ 200,000
* 1 ≤ M ≤ 1,000,000

### Sample Output

```
1 3 5 0 4 2 6
```

### Starter Code (signatures only — no implementation)

```java
package com.student_word;

// You will design the fields/constructors.
class IntNode {
    // e.g., data, next
}

// Linked-list queue of ints.
class LinkedQueue {
    void enqueue(int value) {
        throw new UnsupportedOperationException();
    }

    int dequeue() {
        throw new UnsupportedOperationException();
    }

    boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    int size() {
        throw new UnsupportedOperationException();
    }
}

public class Josephus {
    static void printJosephusOrder(int N, int M) {
        // Enqueue 0..N-1, rotate (M-1) times, dequeue M-th, print order.
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        // Parse N, M from args; call printJosephusOrder(N, M).
        throw new UnsupportedOperationException();
    }
}
```

---

## Grading Criteria

**Functionality (50%)**

* Correct output on provided and hidden tests
* Proper handling of edge cases

**Data Structure Implementation (50%)**

* Custom structures only (no `java.util` collections)
* Correct node/container designs per problem
* Proper resizing behavior in Q2

---

## Submission Tips

* Keep **one public class per file** with the **exact filenames** listed.
* Use `StdIn`/`StdOut` from `algs4.jar` for I/O (no `Scanner`/`System.out` mixing).
* Make sure your program **compiles and runs** with the commands shown above.

---

# Connect IntelliJ to Your Private GitHub Classroom Repo

## 1) Get your private repo (GitHub Classroom)

1. Open the **assignment invitation link** from your instructor/TA.
2. Click **Accept assignment**. Classroom creates a **private repository** for you in the course org. Some assignments include starter code/tests; others may be empty.

---

## 2) Create a Fine-Grained Personal Access Token (PAT)

Use a **fine-grained** PAT to authenticate Git operations (clone/fetch/pull/push) from IntelliJ.

1. GitHub → **Settings → Developer settings → Personal access tokens → Fine-grained tokens → Generate new token**
2. **Resource owner:** select your course org (e.g., `SMU-CompSci`).
3. **Repository access:** choose **All repositories** (the org only gives you private assignment repos).
4. **Repository permissions:** **Repository → Contents: Read and write**. (*Metadata: Read* is fine.)
5. Choose an **Expiration** (e.g., end of term), **Generate**, and **copy** the token (store it securely).
6. If org approval/SSO is required, notify the TA so they can grant access.
7. After approval, you can pull/push for the rest of the term.

**Classic scopes ↔ Fine-grained permissions (reference):**

* `repo` → **Repository / Contents (read & write)** (sufficient for pushes)
* No admin/Actions permissions required for basic Git.

---

## 3) Add your token/account to IntelliJ

1. IntelliJ: **File → Settings → Version Control → GitHub → Add account → Log In with Token**
2. Paste token → **Add Account**
3. Verify Git path: **Settings → Version Control → Git → Test**

---

## 4) Import (clone) your repo into IntelliJ

1. In GitHub, open your private repo → **Code → copy URL**
2. IntelliJ (Welcome) **Get from VCS** (or **File → New → Project from Version Control**) → paste URL → choose folder → **Clone**
3. **Trust** the project when prompted; Gradle will auto-import

---

## 5) Basic Git in IntelliJ: Pull → Commit → Push

* **Pull/Fetch:** **Git → Pull** / **Fetch**
* **Commit:** Use the **Commit** tool window → message → **Commit**
* **Push:** **Git → Push…** (uses your saved token)

---

## 6) Quick checks & common fixes

* **Non-fast-forward / push rejected:** **Pull** (optionally **rebase**), resolve conflicts, then **Push**
* **403 / auth failed:** Token must be **fine-grained**, **owned by course org**, with **Repository → Contents: Read & write**; confirm org approval
* **Gradle import issues:** Re-open from `build.gradle` or **Reload All Gradle Projects**; if needed, **Invalidate Caches / Restart**

---
