plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.sonarqube.plugin.lib)
    implementation(libs.spotless.plugin.lib)
    implementation(libs.spring.boot.plugin.lib)
    implementation(libs.spring.dependency.plugin.lib)
    implementation(libs.test.logger.plugin.lib)
}