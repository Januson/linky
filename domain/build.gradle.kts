plugins {
    id("java-conventions")
    id("spotless-conventions")
}

dependencies {
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    implementation("jakarta.interceptor:jakarta.interceptor-api:2.1.0")
    implementation("jakarta.transaction:jakarta.transaction-api")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
