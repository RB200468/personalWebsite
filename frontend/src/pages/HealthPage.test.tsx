import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { render, screen } from "@testing-library/react";
import { axe } from "vitest-axe";
import { afterEach, describe, expect, it, vi } from "vitest";
import HealthPage from "./HealthPage";
import config from "../../vite.config";

function renderPage() {
  // No retries, so the error state shows up straight away in tests.
  const queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false } },
  });
  return render(
    <QueryClientProvider client={queryClient}>
      <HealthPage />
    </QueryClientProvider>,
  );
}

function stubApi(response: Response | Error) {
  const fetchStub = vi.fn(() =>
    response instanceof Error
      ? Promise.reject(response)
      : Promise.resolve(response),
  );
  vi.stubGlobal("fetch", fetchStub);
  return fetchStub;
}

function healthyResponse() {
  return Response.json({ status: "UP" });
}

afterEach(() => {
  vi.unstubAllGlobals();
});

describe("HealthPage", () => {
  it("shows the status from the health endpoint", async () => {
    const fetchStub = stubApi(healthyResponse());
    renderPage();

    expect(await screen.findByText(/API status: UP/)).toBeInTheDocument();

    const request = (fetchStub.mock.calls[0] as unknown as [Request])[0];
    expect(new URL(request.url).pathname).toBe("/api/health");
  });

  it("announces loading to screen readers", () => {
    stubApi(new Response(null)); // never inspected: we check before it settles
    renderPage();

    expect(screen.getByRole("status")).toHaveTextContent(/loading/i);
  });

  it("shows an accessible error when the backend is down", async () => {
    stubApi(new Error("connection refused"));
    renderPage();

    expect(await screen.findByRole("alert")).toHaveTextContent(
      /could not reach the api/i,
    );
  });

  it("shows an accessible error when the API answers with a failure", async () => {
    stubApi(new Response("Bad gateway", { status: 502 }));
    renderPage();

    expect(await screen.findByRole("alert")).toBeInTheDocument();
  });

  it("uses a main landmark and a single h1", async () => {
    stubApi(healthyResponse());
    renderPage();
    await screen.findByText(/API status: UP/);

    expect(screen.getByRole("main")).toBeInTheDocument();
    expect(screen.getAllByRole("heading", { level: 1 })).toHaveLength(1);
  });

  it.each([
    ["loaded", () => stubApi(healthyResponse()), /API status: UP/],
    ["error", () => stubApi(new Error("down")), /could not reach/i],
  ])("has no axe violations when %s", async (_name, stub, ready) => {
    stub();
    const { container } = renderPage();
    await screen.findByText(ready);

    expect(await axe(container)).toHaveNoViolations();
  });
});

describe("dev server", () => {
  it("proxies /api to the backend on port 8080", () => {
    expect(config.server?.proxy).toEqual({ "/api": "http://localhost:8080" });
  });
});
