plugins {
    id("org.springframework.boot") version "2.3.2.RELEASE"
}

dependencies {
    implementation(project(":adapters:in:web"))
    implementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter")
}