val testcontainersVersion = "1.15.0"

dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("net.jodah:failsafe:2.4.0")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("com.github.tomakehurst:wiremock-jre8:2.27.2")
//    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
//    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
//    testImplementation("org.testcontainers:mockserver:$testcontainersVersion")
//    testImplementation("org.mock-server:mockserver-client-java:5.11.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
