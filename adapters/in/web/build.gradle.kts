dependencies {
    implementation(project(":domain"));
//    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:javax.servlet-api");
//    testImplementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
