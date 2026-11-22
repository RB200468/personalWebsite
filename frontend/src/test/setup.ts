import "@testing-library/jest-dom/vitest";
import { cleanup } from "@testing-library/react";
import { afterEach, expect } from "vitest";
import * as axeMatchers from "vitest-axe/matchers";
import { client } from "../api/generated/client.gen";

expect.extend(axeMatchers);

// Node cannot build a Request from a relative URL like /api/health. Browsers can.
client.setConfig({ baseUrl: "http://localhost" });

afterEach(cleanup);
