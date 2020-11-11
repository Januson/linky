plugins {
    id("org.springframework.boot") version "2.3.2.RELEASE"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":adapters:in:web"))
    implementation(project(":adapters:out:events"))
    implementation(project(":adapters:out:persistence"))

    implementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.h2database:h2")
}