# Code review

The agent reviews its own work before opening a PR. The user reviews and merges.

## Before opening a PR

1. Run `/code-review low` on the branch diff.
2. Fix real findings. For ones you disagree with, note why in the PR.
3. Re-run the tests after any fix.

## What to check

- **Spec:** every acceptance criterion in the issue is met and nothing outside it was changed.
- **Tests:** each criterion has a test that failed first. No test was weakened or skipped.
- **Readability:** a human can follow it. No clever abstractions.
- **Standards:** follows `programming.md`. Generated code is untouched.
- **Accessibility:** the WCAG 2.2 AA criteria are met on any UI change.
- **Safety:** no secrets, DB files or build output in the diff.

## In the PR

Say what you reviewed and what you fixed. If a finding was left alone, say why.

The user decides when to merge. The agent never merges its own PR.
