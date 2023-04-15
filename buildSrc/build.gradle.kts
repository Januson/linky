plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.9.0")
    implementation("com.adarshr:gradle-test-logger-plugin:3.2.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.7.2")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.12.RELEASE")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
}