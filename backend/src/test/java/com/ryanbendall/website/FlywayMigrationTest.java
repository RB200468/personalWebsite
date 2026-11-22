package com.ryanbendall.website;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class FlywayMigrationTest {

    @TempDir
    static Path tempDir;

    static Path databaseFile;

    @DynamicPropertySource
    static void useTemporaryDatabase(DynamicPropertyRegistry registry) {
        databaseFile = tempDir.resolve("migration-test.db");
        registry.add("app.database.path", databaseFile::toString);
    }

    @Autowired
    JdbcClient jdbcClient;

    @Test
    void startingTheAppCreatesTheDatabaseFileAndAppliesTheFirstMigration() {
        assertThat(databaseFile).exists();

        Integer appliedMigrations = jdbcClient
                .sql("SELECT COUNT(*) FROM flyway_schema_history WHERE success = 1")
                .query(Integer.class)
                .single();
        assertThat(appliedMigrations).isGreaterThanOrEqualTo(1);

        Integer placeholderTables = jdbcClient
                .sql("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name = 'placeholder'")
                .query(Integer.class)
                .single();
        assertThat(placeholderTables).isEqualTo(1);
    }
}
