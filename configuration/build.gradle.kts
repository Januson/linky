import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    application
    id("org.springframework.boot")
    id("base-conventions")
    id("java-conventions")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":adapters:in:web"))
    implementation(project(":adapters:in:jobs"))
    implementation(project(":adapters:out:events"))
    implementation(project(":adapters:out:geo-encoder"))
    implementation(project(":adapters:out:persistence"))

    implementation("jakarta.servlet:jakarta.servlet-api");
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("com.h2database:h2")
}

val appName = rootProject.name
val appVersion = project.version.toString()
val artifactName = "$appName-$appVersion.jar"

application {
    mainClass.set("linky.configuration.LinkyApplication")
    applicationName = appName
}

springBoot {
    buildInfo {
        properties {
            artifact.set(artifactName)
            version.set(appName)
            name.set(rootProject.name)
        }
    }
}

tasks {

    bootJar {
        archiveBaseName.set(appName)
        archiveVersion.set(appVersion)

        if (project.hasProperty("archiveName")) {
            archiveFileName.set(project.properties["archiveName"] as String)
        }
    }

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

tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName.set("linky.org/$appName")
}
