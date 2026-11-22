# Workflow

How an issue goes from Todo to Done.

1. Read the issue and the spec it links to. If the spec is unclear, ask. Do not guess.
2. Update `release/v1.0.0` and branch from it, using the issue's Linear branch name (`<user>/eng-N-slug`). Never branch from or push to `main`. Move the issue to **In Progress**.
3. Work test-first (see `testing.md`). Keep the change inside the issue's scope.
4. Run the full relevant test suite, then commit. Do not open a PR yet. Move the issue to **In Review**.
5. Start an independent reviewer agent on the branch (see `code-review.md`). Post its result as a comment on the Linear issue.
   - **FAIL:** move the issue back to **In Progress**, fix the findings, commit, move it to **In Review** and start a new reviewer. After 3 FAILs, stop and ask the user.
   - **PASS:** go to step 6.
6. Push the branch and open a PR with `--base release/v1.0.0`. The body ends with `Closes ENG-N`. Post the reviewer's result as a comment on the PR. Move the issue to **In Human Review** and tell the user it is ready. Stop there.
7. The user reviews and merges. If the user asks for changes, move the issue back to **In Progress**. Fix them, then repeat steps 4 to 6. Push to the same PR instead of opening a new one.
8. Mark the issue **Done** only after the user says the PR is merged.

Every code change has a Linear issue. If there is none, create one before starting.

## Issue states

| State | Meaning | Moved by |
| --- | --- | --- |
| In Progress | Being built or fixed. | Agent |
| In Review | The independent reviewer agent is checking the branch. No PR yet. | Agent |
| In Human Review | The reviewer passed and the PR is open. Waiting for the user. | Agent |
| Done | The user merged the PR. | Agent, after the user says so |

The agent opens a PR only after the reviewer passes. The agent never merges a PR. The only move past **In Human Review** is to **Done**, and only after the user says the PR is merged.

## Commits and PRs

- One issue, one branch, one PR. Small commits are fine.
- Commit messages start with the issue ID, for example `ENG-3: Add health endpoint`.
- Write PR bodies with the `pr` skill.
- Run `git` and `gh` as standalone commands. Do not put `cd` or another command before them, or the sandbox exclusion will not apply.

## When the work diverges from the issue

Say so in a Linear comment and in the PR. Do not change the issue description.
