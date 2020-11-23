dependencies {
//    implementation("jakarta.transaction:jakarta.transaction-api:2.0.0")
    implementation("javax.transaction:javax.transaction-api:1.3")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

