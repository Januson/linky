plugins {
    id("base-conventions")
    id("java-conventions")
}

dependencies {
    implementation(project(":domain"));
    implementation("jakarta.servlet:jakarta.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
