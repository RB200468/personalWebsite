## Agent skills

### Issue tracker

Issues are tracked in Linear via the Linear MCP server. See `docs/agents/issue-tracker.md`.

### Domain docs

Single-context: one `GLOSSARY.md` and `docs/adr/` at the repo root. See `docs/agents/domain.md`.

### Agent rules

Read the relevant file before the matching work:

- `docs/agents/workflow.md`: branching, commits, PRs, issue states, closing issues. Branch from and PR into `release/v1.0.0`, never `main`.
- `docs/agents/testing.md`: strict test-first on every issue.
- `docs/agents/programming.md`: code style, stack rules, accessibility.
- `docs/agents/debugging.md`: reproduce, find the cause, then fix.
- `docs/agents/code-review.md`: independent reviewer agent checks every PR. PASS goes to **In Human Review**, FAIL goes back to **In Progress**.

## Project context

Read `docs/context.md` for what we're building and the decisions made. Keep it updated when decisions change.

## Working agreements

- Specs are reviewed and agreed before implementation starts.
- Issues stay small, each with a testable "done" condition.
- Routine implementation runs at lower effort or on a cheaper model. Only planning and design use the heavier setting. The user is on a Claude Pro plan, so be mindful of long, complex tasks.
- Use `/clear` between unrelated tasks.
- Code must be readable by a human. Prefer plain, conventional React and Spring over clever abstractions.
- Accessibility (WCAG 2.2 AA) is part of the acceptance criteria on every issue.
