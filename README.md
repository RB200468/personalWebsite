# Personal website

A portfolio and blog with an admin page for writing content. See `docs/context.md` for what we're building and the decisions made.

## Layout

| Folder | What it holds |
| --- | --- |
| `frontend/` | Vite, React and TypeScript app |
| `backend/` | Spring Boot API and SQLite database access |
| `contract/` | OpenAPI file that both sides are generated from |
| `docs/` | Context, ADRs and agent docs |

## Commands

| Task | Command |
| --- | --- |
| Run the backend (port 8080) | coming in a later issue |
| Run the frontend (Vite dev server) | coming in ENG-4 |
| Check the contract generates the health interface | `backend/scripts/check-codegen.sh` |
| Run frontend tests | coming in ENG-4 |
| Regenerate backend API interfaces from the contract | `cd backend && ./gradlew generateApi` |

## Generated code

`contract/openapi.yaml` is hand-written. The backend's Spring interfaces and models are generated from it into `backend/build/generated/openapi/`, which is not committed. Never edit generated code. Change the contract, then run `cd backend && ./gradlew generateApi`. An invalid contract fails the build.
