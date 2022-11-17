plugins {
    id("base-conventions")
    id("io.micronaut.library")
}

val micronautVersion by extra("3.7.4")

dependencies {
    implementation(project(":domain"));
    implementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.micronaut:micronaut-validation")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


//micronaut {
//    version("3.7.4")
//}
