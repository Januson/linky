plugins {
    java
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
