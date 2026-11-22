package com.ryanbendall.website;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Checks the running API against contract/openapi.yaml. If a response drifts from the
 * contract (wrong shape, wrong status, wrong content type), these tests fail.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ContractConformanceTest {

    // Gradle runs tests with the backend/ folder as the working directory.
    private static final String CONTRACT = Path.of("..", "contract", "openapi.yaml").toString();

    @TempDir
    static Path tempDir;

    @DynamicPropertySource
    static void useTemporaryDatabase(DynamicPropertyRegistry registry) {
        registry.add("app.database.path", () -> tempDir.resolve("contract-test.db").toString());
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    void healthResponseMatchesTheContract() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(openApi().isValid(CONTRACT));
    }
}
