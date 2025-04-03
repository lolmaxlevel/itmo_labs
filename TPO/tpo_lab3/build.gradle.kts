plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.lolmaxlevel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Selenium
    implementation("org.seleniumhq.selenium:selenium-java:4.30.0")
    implementation("io.github.bonigarcia:webdrivermanager:6.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.7")


    // Test
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}


tasks.test {
    useJUnitPlatform()

    // Enable parallel execution
    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
    systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
}
kotlin {
    jvmToolchain(23)
}