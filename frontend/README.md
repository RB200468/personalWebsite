# frontend

The Vite, React and TypeScript single-page app.

## Commands

Run these in `frontend/`.

| Task | Command |
| --- | --- |
| Install dependencies | `npm install` |
| Start the dev server (port 5173) | `npm run dev` |
| Run tests (Vitest, Testing Library, axe) | `npm test` |
| Type-check | `npm run typecheck` |
| Production build | `npm run build` |
| Regenerate the API client | `npm run generate` |

## API client

The client in `src/api/generated/` is generated from `../contract/openapi.yaml` and is not committed. Never edit it by hand. `dev`, `build`, `test` and `typecheck` regenerate it first.

In dev, Vite proxies `/api` to the backend on `http://localhost:8080`, so there is no CORS configuration. Start the backend first (`cd backend && ./gradlew bootRun`).
