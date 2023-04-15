plugins {
    java
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    id("org.sonarqube") version "4.2.1.3168"
    id("com.diffplug.spotless")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

sonarqube {
    properties {
        properties["sonar.host.url"] = "https://sonarcloud.io"
        properties["sonar.organization"] = "januson-github"
        properties["sonar.projectKey"] = "Januson_linky"
        properties["sonar.login"] = System.getenv("SONAR_TOKEN")
    }
}
