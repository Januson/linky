pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    plugins {
        id("com.adarshr.test-logger") version "3.2.0"
    }
}

rootProject.name = "linky"

include(":adapters:in:jobs")
include(":adapters:in:web")
include(":adapters:out:events")
include(":adapters:out:geo-encoder")
include(":adapters:out:persistence")
include("acceptance-tests")
include("configuration")
include("domain")
