# Debugging

Use the `diagnosing-bugs` skill for anything broken, failing or slow.

## Rules

- Reproduce first. Write a failing test or exact command that shows the bug before changing code.
- Find the cause before fixing. Read the error and the code path. Do not try random changes.
- Change one thing at a time and re-run the reproduction.
- Fix the cause, not the symptom. Do not silence errors, add blind retries or catch and ignore exceptions.
- Keep the reproduction as a regression test.
- Stop and ask the user after two failed attempts at the same fix, or if the fix needs a sandbox, settings or credential change.

## Reporting

State the cause, the fix and the test that now covers it. If the cause is not found, say so and list what was ruled out.
