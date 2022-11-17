//import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    application
//    id("org.springframework.boot") version "2.7.2"
//    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application")
    id("base-conventions")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":adapters:in:web"))
    implementation(project(":adapters:in:jobs"))
    implementation(project(":adapters:out:events"))
    implementation(project(":adapters:out:geo-encoder"))
    implementation(project(":adapters:out:persistence"))

    implementation("javax.servlet:javax.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    annotationProcessor("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("jakarta.annotation:jakarta.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.h2database:h2")
}

val appName = rootProject.name
val appVersion = project.version.toString()
val artifactName = "$appName-$appVersion.jar"

application {
    mainClass.set("linky.configuration.LinkyApplication")
    applicationName = appName
}

//springBoot {
//    buildInfo {
//        properties {
//            artifact = artifactName
//            version = appName
//            name = rootProject.name
//        }
//    }
//}

tasks {

//    bootJar {
//        archiveBaseName.set(appName)
//        archiveVersion.set(appVersion)
//
//        if (project.hasProperty("archiveName")) {
//            archiveFileName.set(project.properties["archiveName"] as String)
//        }
//    }

//    "asciidoctor"(AsciidoctorTask::class) {
//        dependsOn(":test")
//        inputs.dir("build/generated-snippets")
////        sourceDir = file("docs")
//        sources(delegateClosureOf<PatternSet> {
//            include("toplevel.adoc", "another.adoc", "third.adoc")
//        })
//        outputDir = file("build/docs")
//    }
}

//tasks.getByName<BootBuildImage>("bootBuildImage") {
//    imageName = "linky.org/$appName"
//}

graalvmNative.toolchainDetection.set(false)

micronaut {
    version("3.7.4")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("linky.*")
    }
}
