plugins {
    id("com.diffplug.spotless")
}

spotless {

    format("misc") {
        target("*.gradle.kts", "buildSrc/**/*.gradle.kts", "*.gitignore")
        targetExclude("**/build/**")
        trimTrailingWhitespace()
        endWithNewline()
    }

    pluginManager.withPlugin("java") {

        val javaFormatterConfigFile = rootProject.file("src/eclipse/linky-formatter-settings.xml")

        java {
            palantirJavaFormat()
            eclipse().configFile(javaFormatterConfigFile)
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        kotlin {
            targetExclude("**/src/test/resources/**")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}
