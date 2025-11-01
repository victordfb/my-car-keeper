plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.smartfuture"
version = "0.0.1-SNAPSHOT"
description = "App to support keeping your cars"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Core Spring Boot (already present – gives auto-configuration, actuator, etc.)
    implementation("org.springframework.boot:spring-boot-starter")

    // ---- ADD THESE FOR WEB & JPA ----
    implementation("org.springframework.boot:spring-boot-starter-web")      // REST / MVC
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA + Hibernate

    // Kotlin reflection (required by Spring when using Kotlin)
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ---- OPTIONAL BUT RECOMMENDED ----
    // In-memory DB for quick local development / tests
    runtimeOnly("com.h2database:h2")

    // Hot-reload during development
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // ---- TEST DEPENDENCIES ----
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
