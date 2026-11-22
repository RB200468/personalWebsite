# Testing

Every issue is built test-first (red, green, refactor). Use the `tdd` skill.

## The loop

1. Turn one acceptance criterion into a failing test. Run it and see it fail for the right reason.
2. Write the smallest code that makes it pass.
3. Refactor with the tests green.
4. Repeat for the next criterion.

## Issues with no application code

Docs, config and scaffold issues still start from a check. Write the "done when" check first, for example a command that must succeed or a file that must exist. Run it to see it fail. Make the change. Run it to see it pass.

## Test layers

| Layer | Tool | Notes |
| --- | --- | --- |
| Backend | JUnit, Spring Boot tests | Use a temporary SQLite file. Also check the running API matches `contract/openapi.yaml`. |
| Frontend | Vitest, Testing Library | Query by role and label, like a user would. |
| Accessibility | axe | Automated check on every page. Part of the acceptance criteria on every UI issue. |
| End to end | Playwright | One smoke test only: log in, publish a post, see it on the blog. |

## Rules

- Test behavior through public interfaces. Do not test private details.
- Do not mock the database. Use a temporary SQLite file.
- A test must be able to fail. Never weaken or delete a test to make it pass.
- Run the full relevant test suite before committing. Report failures with their output. Never claim tests pass without running them.
