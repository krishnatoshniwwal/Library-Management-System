# Library Management System

Capstone project, BC2025047.

A command-line program that manages a college library's items, members and loans. It reads one command per line from standard input and prints the result of each command. Time is simulated: the library starts on day 0 and only `ADVANCE` moves it forward.

## How to run

```
javac *.java
java Main < tests/sample.txt
```

## How to test

Run every test at once:

- Windows: `run_tests.bat`
- Linux/macOS: `./run_tests.sh`

Each test runs `tests/<name>.txt` and compares the output with `tests/<name>_expected.txt`.

| Test file | What it checks |
|---|---|
| `sample.txt` | The sample session from the brief |
| `errors.txt` | Every error in section 7 of the brief |
| `limits.txt` | A Student, a Staff and a Faculty member each reaching their limit |
| `fines.txt` | Due days, fines for Book (₹2/day) and Magazine (₹1/day), and `OVERDUE` |
| `reserve.txt` | `RESERVE`, its errors, and auto-issue on return |
| `invalid.txt` | Badly formed input and duplicate IDs |

## Design

The class diagram is in `UML.pdf`.

- **Items:** `Book`, `Magazine` and `ReferenceBook` extend the abstract class `LibraryItem`. Only `Book` and `Magazine` implement `Borrowable`, which gives the fine rate.
- **Members:** `Student`, `Staff` and `Faculty` extend the abstract class `Member`. Each one sets its own item limit and loan period.
- **Loan:** links one item to one member, and calculates the due day, days late and fine.
- **Library:** holds the items, members, active loans, reservations and today's day. It checks the rules and throws the exceptions.
- **CommandProcessor:** reads commands, calls `Library`, prints results, and catches every `LibraryException` in one place.
- **Exceptions:** every error is a subclass of `LibraryException`.

## Extras beyond the brief

- `RESERVE` command (the optional bonus).
- Input validation (`InputValidator`, `InvalidInputException`).
- Duplicate-ID checking (`DuplicateIdException`).
