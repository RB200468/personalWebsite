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
| Run the frontend (Vite dev server, port 5173) | `cd frontend && npm run dev` |
| Check the contract generates the health interface | `backend/scripts/check-codegen.sh` |
| Run all backend tests (includes the contract check) | `cd backend && ./gradlew test` |
| Run all frontend tests (includes the axe accessibility check) | `cd frontend && npm test` |
| Regenerate backend API interfaces from the contract | `cd backend && ./gradlew generateApi` |
| Regenerate the frontend API client from the contract | `cd frontend && npm run generate` |

## Generated code

`contract/openapi.yaml` is hand-written. The backend's Spring interfaces and models are generated from it into `backend/build/generated/openapi/`, which is not committed. Never edit generated code. Change the contract, then run `cd backend && ./gradlew generateApi`. An invalid contract fails the build.

The frontend client is generated into `frontend/src/api/generated/`, which is not committed either. `npm run dev`, `npm run build` and `npm test` regenerate it first.
