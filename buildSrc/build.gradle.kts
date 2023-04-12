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
}