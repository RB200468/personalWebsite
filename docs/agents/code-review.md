# Code review

An independent reviewer agent checks every PR. The agent that wrote the code never judges it. The user then reviews and merges.

## When

After the PR is open and the issue is in **In Review** (see `workflow.md`).

## Starting the reviewer

Start a fresh subagent with the Agent tool, using the `Explore` type so it can read and run commands but cannot edit files. Do not fork yourself and do not reuse an earlier reviewer. It must have no context from the implementation.

Give it only this:

- The issue ID, and the instruction to read the issue and the spec it links to.
- The PR number and the base branch `release/v1.0.0`. It reads the diff with `git diff release/v1.0.0...HEAD`.
- The instruction to read `docs/agents/` and `docs/context.md`, then run the `code-review` skill.
- The checklist below.
- The output format below.

Do not tell it what you think of the code or which parts to go easy on.

## What the reviewer checks

- **Spec:** every acceptance criterion in the issue is met and nothing outside it was changed. Changes beyond the issue are explained in the PR.
- **Tests:** each criterion has a test that failed first. No test was weakened or skipped. The tests pass when run.
- **Readability:** a human can follow it. No clever abstractions.
- **Standards:** follows `programming.md`. Generated code is untouched.
- **Accessibility:** the WCAG 2.2 AA criteria are met on any UI change.
- **Safety:** no secrets, DB files or build output in the diff.

## What the reviewer returns

The first line is `VERDICT: PASS` or `VERDICT: FAIL`. Then a list of findings, each with a file and line, a severity and what to change.

- **Blocking:** a missed criterion, a bug, a weakened test, a broken rule. Any blocking finding means FAIL.
- **Non-blocking:** a suggestion. These alone mean PASS.

## After the verdict

1. Post the reviewer's result unchanged as a PR comment and as a Linear comment.
2. On PASS, move the issue to **In Human Review**. On FAIL, move it back to **In Progress**. The full flow is in `workflow.md`.
3. On FAIL, fix the blocking findings and re-run the tests. Do not argue a verdict away. If you disagree with a finding, say why in the PR and let the user decide.
4. Each new review is a new reviewer agent.

The user decides when to merge. The agent never merges its own PR.
