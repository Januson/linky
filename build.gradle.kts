plugins {
    java
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

subprojects {

    group = "linky"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "java-library")

    repositories {
        jcenter()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.2.RELEASE"))
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }

    plugins.withType<JavaPlugin>().configureEach {
        configure<JavaPluginExtension> {
            modularity.inferModulePath.set(true)
        }
    }

}
