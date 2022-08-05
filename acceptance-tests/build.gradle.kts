plugins {
    jacoco
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

val spekVersion = "2.0.13"
val junitVersion = "5.7.2"
val testContainersVersion = "1.16.0"

dependencies {
    implementation(project(":domain"))

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    testImplementation(gradleTestKit())
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")

}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}
