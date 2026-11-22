## Agent skills

### Issue tracker

Issues are tracked in Linear via the Linear MCP server. See `docs/agents/issue-tracker.md`.

### Domain docs

Single-context: one `GLOSSARY.md` and `docs/adr/` at the repo root. See `docs/agents/domain.md`.

## Project context

Read `docs/context.md` for what we're building and the decisions made. Keep it updated when decisions change.

## Working agreements

- Specs are reviewed and agreed before implementation starts.
- Issues stay small, each with a testable "done" condition.
- Routine implementation runs at lower effort or on a cheaper model. Only planning and design use the heavier setting. The user is on a Claude Pro plan, so be mindful of long, complex tasks.
- Use `/clear` between unrelated tasks.
- Code must be readable by a human. Prefer plain, conventional React and Spring over clever abstractions.
- Accessibility (WCAG 2.2 AA) is part of the acceptance criteria on every issue.
