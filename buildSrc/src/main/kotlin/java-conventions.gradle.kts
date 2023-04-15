plugins {
    `java-library`
    jacoco
    checkstyle
    id("com.adarshr.test-logger")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.5"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    modularity.inferModulePath.set(true)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

checkstyle {
    toolVersion = "8.45.1"
}

tasks.register("checkstyle") {
    dependsOn(tasks.checkstyleMain, tasks.checkstyleTest)
}

tasks.withType<Test> {
    finalizedBy(tasks.jacocoTestReport)

    useJUnitPlatform()
}
