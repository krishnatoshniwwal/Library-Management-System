@echo off
rem Compiles, then runs every test and compares it with the expected output
javac *.java || exit /b 1
for %%t in (sample errors limits fines reserve invalid) do (
  java Main < tests\%%t.txt > tests\%%t.out
  fc tests\%%t.out tests\%%t_expected.txt > nul && (echo PASS %%t) || (echo FAIL %%t & fc tests\%%t.out tests\%%t_expected.txt)
)
