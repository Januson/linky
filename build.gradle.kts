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

tasks.register("coverage") {
    dependsOn("jacocoTestReport")
}

allprojects {
    group = "linky"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "jacoco")

}

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.2"))
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        modularity.inferModulePath.set(true)
    }

}
