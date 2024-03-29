plugins {
    id("base-conventions")
}

dependencies {
    implementation(project(":domain"));

    implementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.awaitility:awaitility")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
