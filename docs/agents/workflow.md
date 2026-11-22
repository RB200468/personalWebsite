# Workflow

How an issue goes from Todo to Done.

1. Read the issue and the spec it links to. If the spec is unclear, ask. Do not guess.
2. Update `release/v1.0.0` and branch from it, using the issue's Linear branch name (`<user>/eng-N-slug`). Never branch from or push to `main`. Move the issue to **In Progress**.
3. Work test-first (see `testing.md`). Keep the change inside the issue's scope.
4. Self-review (see `code-review.md`), fix the findings, then commit.
5. Push the branch and open a PR with `--base release/v1.0.0`. The body ends with `Closes ENG-N`. Move the issue to **In Review**.
6. The user reviews and merges. If changes are requested, move the issue back to **In Progress**, then to **In Review** again when the PR is updated.
7. Mark the issue **Done** only after the user says the PR is merged.

Every code change has a Linear issue. If there is none, create one before starting.

## Commits and PRs

- One issue, one branch, one PR. Small commits are fine.
- Commit messages start with the issue ID, for example `ENG-3: Add health endpoint`.
- Write PR bodies with the `pr` skill.
- Run `git` and `gh` as standalone commands. Do not put `cd` or another command before them, or the sandbox exclusion will not apply.

## When the work diverges from the issue

Say so in a Linear comment and in the PR. Do not change the issue description.
