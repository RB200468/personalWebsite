plugins {
    java
    id("org.springframework.boot") version "4.1.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.27.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    runtimeOnly("org.xerial:sqlite-jdbc:3.54.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-jdbc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    // Keep temporary test databases inside build/ (git-ignored) instead of the system temp folder.
    val testTmpDir = layout.buildDirectory.dir("tmp/test-files").get().asFile
    doFirst { testTmpDir.mkdirs() }
    systemProperty("java.io.tmpdir", testTmpDir.path)
}

// Spring interfaces and models are generated from the contract into build/,
// which is not committed. Never edit the generated code; change
// contract/openapi.yaml and re-run `./gradlew generateApi`.
tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateApi") {
    generatorName.set("spring")
    inputSpec.set(rootProject.file("../contract/openapi.yaml").path)
    outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.path)
    apiPackage.set("com.ryanbendall.website.api")
    modelPackage.set("com.ryanbendall.website.api.model")
    // Fail the build if the contract is invalid.
    validateSpec.set(true)
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true",
            "useTags" to "true",
            "documentationProvider" to "none",
            "openApiNullable" to "false",
        )
    )
}

// Compile the generated interfaces together with our own code.
sourceSets {
    main {
        java {
            srcDir(layout.buildDirectory.dir("generated/openapi/src/main/java"))
        }
    }
}

tasks.compileJava {
    dependsOn("generateApi")
}
