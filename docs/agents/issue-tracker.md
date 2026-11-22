# Issue tracker: Linear

Issues and specs for this repo live in Linear. Use the Linear MCP server's tools for all operations (no CLI).

## Conventions

- **Create an issue**: use the Linear MCP's create-issue tool with a title and a markdown description.
- **Read an issue**: use the Linear MCP's get-issue tool with the issue identifier (e.g. `ENG-123`), including comments and labels.
- **List issues**: use the Linear MCP's list-issues tool, filtering by team, state, and label as needed.
- **Comment on an issue**: use the Linear MCP's create-comment tool.
- **Apply / remove labels**: update the issue's labels via the Linear MCP's update-issue tool.
- **Close**: update the issue's state to Done (or Canceled) via update-issue, with a closing comment.
- **Sub-issues**: set the parent issue when creating the child. Where unsupported, put `Part of <ID>` at the top of the child description.

Tool names vary by MCP version; list the available `linear` MCP tools if one isn't found. If the Linear MCP isn't connected, tell the user instead of falling back to another tracker.

## When a skill says "publish to the issue tracker"

Create a Linear issue.

## When a skill says "fetch the relevant ticket"

Read it as in **Read an issue** above. Linear identifiers look like `ENG-123`.

## Linear conventions

- **Team**: use the `ENG` team for all issues. (A `PRO` team exists but is unused for now.)
- **Type labels**: `Bug`, `Feature`, `Improvement`.
- **Workflow labels** (who acts next): `ready-for-agent` (spec is clear enough to implement without asking), `ready-for-human` (needs judgment, design taste, or credentials), `needs-info` (blocked on an answer). Use `addLabels`/`removeLabels` so type labels aren't dropped.
- **Statuses**: Backlog → Todo → In Progress → In Review → In Human Review → Done. "In Review" means the independent reviewer agent is checking the PR. "In Human Review" means the reviewer passed and the PR is waiting for the user. A FAIL sends the issue back to In Progress. See `workflow.md`.
- **Descriptions**: human-readable problem and acceptance criteria first. Put plans, file lists, and repro logs in a collapsible `+++ Agent notes` section at the end.
- **Branches and PRs**: include the issue ID (e.g. `ENG-12-add-blog-page`) so Linear links them.
- **Before creating**: search with `list_issues` for duplicates.

## Specs

- **Project per component**: each component or feature (e.g. "Blog", "Homepage", "Contact form") is a Linear Project on the `ENG` team. Create the project when you start specifying that component, not before. Check `list_projects` first to avoid duplicates.
- **Spec as a Document**: the spec lives as a Linear Document inside its project (`save_document`). It covers what and why, decisions made, and what's out of scope.
- **Issues are slices of the spec**: break the spec into small, implementable issues in the same project, each linking back to the spec document. Label them `ready-for-agent` once clear enough to implement.
- **When a skill says "publish a spec" (e.g. `to-spec`)**: create or update the project's spec Document, not an issue.
- **Stays in the repo**: domain vocabulary goes in `GLOSSARY.md` and architecture decisions in `docs/adr/`, since they describe the code and should change with it.
