import { useQuery } from "@tanstack/react-query";
import { getHealth } from "../api/generated";
import styles from "./HealthPage.module.css";

export default function HealthPage() {
  const health = useQuery({
    queryKey: ["health"],
    // throwOnError makes HTTP failures reject, so TanStack Query sees them as errors.
    queryFn: async () => (await getHealth({ throwOnError: true })).data,
  });

  return (
    <main className={styles.page}>
      <h1>Health check</h1>
      {health.isPending && <p role="status">Loading API status…</p>}
      {health.isError && (
        <p role="alert">Could not reach the API. Check that the backend is running.</p>
      )}
      {health.isSuccess && <p role="status">API status: {health.data.status}</p>}
    </main>
  );
}
