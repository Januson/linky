plugins {
    id("base-conventions")
    id("java-conventions")
}

val testcontainersVersion = "1.16.0"

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("dev.failsafe:failsafe:3.2.4")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:2.35.0")
//    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
//    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
//    testImplementation("org.testcontainers:mockserver:$testcontainersVersion")
//    testImplementation("org.mock-server:mockserver-client-java:5.11.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
