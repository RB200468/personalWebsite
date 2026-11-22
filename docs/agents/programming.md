# Programming

The main rule: a human must be able to read and understand this code. Prefer plain, conventional React and Spring over clever abstractions.

## General

- Do only what the issue asks. No extra features, no drive-by refactors.
- Write the simplest code that passes the tests. Add abstraction only after the same pattern appears three times.
- Name things in the project's own terms (see `GLOSSARY.md`).
- Comments explain why, not what.
- Decisions that are big or hard to reverse get an ADR in `docs/adr/`. Update `docs/context.md` when a decision changes.

## Backend (Java 21, Spring Boot)

- Use plain SQL with `JdbcClient` or Spring Data JDBC, and Flyway migrations. No JPA.
- Implement the interfaces generated from `contract/openapi.yaml`. Do not edit generated code.
- Every write endpoint requires a session.

## Frontend (React, TypeScript)

- Strict TypeScript. No `any`.
- Function components, hooks, CSS Modules on the theme variables. No Tailwind.
- Use the generated API client. Do not hand-write fetch calls for contract endpoints.
- Use TanStack Query for server data.

## Contract first

Change `contract/openapi.yaml` first, regenerate the code, then implement.

## Accessibility (WCAG 2.2 AA)

Part of every UI issue's acceptance criteria:

- Semantic HTML, full keyboard use, visible focus.
- Respect `prefers-reduced-motion`.
- Every theme passes AA contrast.
- Pass the axe check.

## Safety

- Never commit secrets, `.env` files or the SQLite database.
- Do not add dependencies without saying why in the PR.
