plugins {
    java
    jacoco
    id("com.adarshr.test-logger")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    modularity.inferModulePath.set(true)
}

//compileJava {
//    options.encoding = "UTF-8"
//    options.compilerArgs << "-parameters"
//}
//
//compileTestJava {
//    options.encoding = "UTF-8"
//}

tasks.withType<Test> {
    finalizedBy(tasks.jacocoTestReport)

    useJUnitPlatform()
}
