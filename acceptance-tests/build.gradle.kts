plugins {
    java
    kotlin("jvm") version "1.4.10"
}

repositories {
    mavenCentral()
}

val spekVersion = "2.0.13"
val junitVersion = "5.4.2"

dependencies {
    implementation(project(":domain"))

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")

}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}
