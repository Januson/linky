import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    checkstyle
    jacoco
    java
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.sonarqube") version "3.0"
}

sonarqube {
    properties {
        properties["sonar.host.url"] = "https://sonarcloud.io"
        properties["sonar.organization"] = "januson-github"
        properties["sonar.projectKey"] = "linky"
        properties["sonar.login"] = System.getenv("SONAR_TOKEN")
    }
}

allprojects {
    group = "linky"
    version = "1.0-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "jacoco")

    jacoco {
        toolVersion = "0.8.6"
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test)
        reports {
            xml.isEnabled = true
        }
    }

}

subprojects {
    apply(plugin = "checkstyle")
    apply(plugin = "java-library")

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.5.RELEASE"))
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

    checkstyle {
        toolVersion = "8.36.2"
    }



    tasks.test {
        finalizedBy(tasks.jacocoTestReport)

        useJUnitPlatform()

        testLogging {
            lifecycle {
                events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
                showStandardStreams = true
            }
            info.events = lifecycle.events
            info.exceptionFormat = lifecycle.exceptionFormat
        }

        val failedTests = mutableListOf<TestDescriptor>()
        val skippedTests = mutableListOf<TestDescriptor>()

        // See https://github.com/gradle/kotlin-dsl/issues/836
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                when (result.resultType) {
                    TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                    TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                    else -> Unit
                }
            }

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    logger.lifecycle("----")
                    logger.lifecycle("Test result: ${result.resultType}")
                    logger.lifecycle(
                            "Test summary: ${result.testCount} tests, " +
                                    "${result.successfulTestCount} succeeded, " +
                                    "${result.failedTestCount} failed, " +
                                    "${result.skippedTestCount} skipped")
                    failedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tFailed Tests")
                    skippedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tSkipped Tests:")
                }
            }

            private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
                logger.lifecycle(subject)
                forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
            }

            private fun TestDescriptor.displayName() = parent?.let { "${it.name} - $name" } ?: name
        })
    }

}
