#!/bin/sh
# Compiles, then runs every tests/X.txt and compares it with tests/X_expected.txt
# (tr -d '\r' removes Windows line endings, so this works on Windows Git Bash too)
javac *.java || exit 1
for f in tests/*_expected.txt; do
  name=$(basename "$f" _expected.txt)
  java Main < "tests/$name.txt" | tr -d '\r' > "tests/$name.out"
  if diff "tests/$name.out" "$f" > /dev/null; then echo "PASS $name"; else echo "FAIL $name"; diff "tests/$name.out" "$f"; fi
done
