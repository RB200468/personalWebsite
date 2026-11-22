plugins {
    java
    id("org.openapi.generator") version "7.27.0"
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
        )
    )
}
