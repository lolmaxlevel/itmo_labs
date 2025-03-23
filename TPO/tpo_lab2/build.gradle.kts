plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.lolmaxlevel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.knowm.xchart:xchart:3.8.3")
    testImplementation("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.junit.platform:junit-platform-engine:1.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("org.junit.platform:junit-platform-suite-api:1.10.2")
    testImplementation("org.junit.platform:junit-platform-suite-engine:1.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testRuntimeOnly("org.junit.platform:junit-platform-console-standalone:1.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}