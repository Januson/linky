plugins {
    java
}

repositories {}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
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
