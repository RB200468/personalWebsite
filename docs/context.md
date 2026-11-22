# Project context

Shared understanding of what we're building, reached by grilling `docs/brief.md`. This is the starting point for specs. When a decision changes, update this file. Big architectural decisions also get an ADR in `docs/adr/`.

## What this is

A personal website for a developer. It is a portfolio and blog. Visitors are recruiters, other developers and people curious about tech. They should find the developer's projects and blog posts easily.

The developer writes content through an admin page in the app. There is one admin account. Hosting is out of scope for v1, so the site and database run locally for now.

## Pages

| Page | Audience | Notes |
| --- | --- | --- |
| Home | Public | A short intro, separate from the About text, and editable in the admin. |
| About | Public | Full About text, markdown, editable in the admin. Contact links (GitHub, LinkedIn, email) live here and in the footer. |
| Blog | Public | List (newest first, simple pagination) and post pages. Markdown rendered with syntax highlighting. |
| Projects | Public | Built last. Curated records managed in the admin, with no live GitHub API calls. No nav link until it exists. |
| Admin (`/admin`) | Admin only | Login, post management, About and Home intro editors. Not linked from public pages. |

## Blog posts

- **Fields:** title, slug (auto-generated, editable), summary, markdown body, status, published date.
- **States:**
  - *Draft*: work in progress, never published. Admin-only.
  - *Published*: visible to everyone.
  - *Archived*: previously published and taken down. Admin-only and can be re-published. The public URL returns 404 and archived posts are not listed or visible in any public way.
- **Delete:** allowed from any state, behind a confirmation.
- **Images:** external URLs in markdown only. No uploads in v1.
- **Not in v1:** tags, search, comments, likes, RSS, newsletter.

## Admin

- **Auth:** a single account, created on first startup from environment variables when no user exists. There is no registration endpoint. Passwords are hashed with bcrypt or Argon2. Login uses a Spring Security session cookie (httpOnly, SameSite) with CSRF protection and rate-limited login attempts. Sessions rather than JWTs, because this is one frontend, one backend and one user.
- **Access:** `/admin` is a normal public URL that shows only a login form. Protection comes from the login, not from hiding the URL. Every write endpoint requires a session.
- **Editor:** a plain accessible textarea with a live markdown preview, not a rich-text editor.

## Design and UX

- Clean, modern, accessible, with the MonkeyType site as the main inspiration.
- Top nav (Home, About, Blog, and Projects once built) with a single centered content column about 65 characters wide and generous whitespace.
- Rounded corners. No gradients. No hero images, carousels or heavy animation.
- Self-hosted fonts: Inter for text and JetBrains Mono for code and small accents. No external CDN.
- **Themes:** default light, default dark, Atom One Dark and Catppuccin Mocha.
  - The first visit follows the OS `prefers-color-scheme`. The choice is saved in `localStorage`.
  - Themes are CSS custom properties switched by a `data-theme` attribute.
  - The selector is small and unobtrusive in the footer, like MonkeyType's picker, and reachable by keyboard and screen reader.
  - Every theme must pass WCAG AA contrast.
- **Accessibility target:** WCAG 2.2 AA. This means semantic HTML, full keyboard navigation with visible focus, a skip-to-content link, `prefers-reduced-motion` respected, and automated axe checks. Accessibility is part of the acceptance criteria on every issue.

## Architecture

- **Monorepo:** `frontend/`, `backend/` and `contract/`.
- **Contract:** a hand-written OpenAPI file at `contract/openapi.yaml`, contract-first. We use the latest OpenAPI version the tooling supports (default 3.1, bump to 3.2 if the generators allow). Decided in ENG-3: OpenAPI 3.1.0, generated with OpenAPI Generator 7.27.0 (`spring` generator, `interfaceOnly`). The TypeScript client and Spring interfaces are generated from it.
- **Backend:** Java 21, Spring Boot 4.1.x, built with Gradle 9.x using the Kotlin DSL. Decided in ENG-2: Spring Boot 3.5 is out of open-source support, and 4.1 needs Java 17+ and Gradle 8.14+ or 9.x. Exact patch versions are pinned at scaffold time. Database access uses plain SQL through Spring `JdbcClient` or Spring Data JDBC, with Flyway migrations. No JPA.
- **Database:** SQLite is the source of truth, since the admin writes to it. Posts and About text are stored as markdown text with their metadata. The DB file is not committed to git. An export-to-markdown backup script comes later.
- **Frontend:** Vite, React and strict TypeScript, with React Router, TanStack Query and the generated client. Styling is plain CSS Modules on the theme variables, with no Tailwind. npm is the package manager.
- **Rendering:** a plain client-rendered SPA. Link previews and SEO are deferred and will be revisited after v1.
- **Local dev:** Vite's dev server proxies `/api` to Spring on port 8080, so there are no CORS settings.
- **Readability rule:** a human must be able to read and understand this code. Prefer plain, conventional React and Spring over clever abstractions.

## Testing

- **Backend:** JUnit and Spring Boot tests against a temporary SQLite file, plus a check that the running API matches `openapi.yaml`.
- **Frontend:** Vitest and Testing Library, with an automated axe check on each page.
- **End to end:** one Playwright smoke test (log in, publish a post, see it on the blog). No more than that.

## Build order

Each item is a Linear Project on the `ENG` team. Each gets a spec Document, then small `ready-for-agent` issues.

1. **Foundation:** monorepo scaffold, contract with a health endpoint, generated client wired in, local dev setup.
2. **Layout and theming:** shell, nav, four themes and the theme selector.
3. **About and Blog (read):** public pages with seed content.
4. **Auth and Admin:** login, then the post, About and Home intro editors.
5. **Accessibility pass and polish.**
6. **Portfolio.**

## Out of scope for v1

Hosting, Postgres, public comments and likes, newsletter, tags, search, RSS, contact form, image uploads, server-side rendering and link previews, live GitHub integration.

## Working agreements

These are also recorded in `CLAUDE.md`.

- Specs are reviewed and agreed before implementation starts.
- Issues stay small, each with a testable "done" condition.
- Routine implementation runs at lower effort or on a cheaper model. Only planning and design use the heavier setting.
- Use `/clear` between unrelated tasks.
- Code favors readability for a human reader.

## Open items

- Confirm the exact Spring Boot 4.1 patch and the Gradle 9 version when the backend is scaffolded (pin them in the Gradle wrapper).
- Revisit link previews and SEO after v1.
- Decide how to back up the SQLite content once real posts exist.
