plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

group = "com.kfaraj.support"
version = "4.0.0"

android {
    namespace = "com.kfaraj.support.appcompat"
    compileSdk = 34
    defaultConfig {
        minSdk = 19
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(libs.androidx.appcompat)
}

extra["pomName"] = "AppCompat"
extra["pomDescription"] = "This library is built on top of the AndroidX AppCompat library."
extra["pomUrl"] = "https://gitlab.com/kfaraj/support#appcompat"

apply(from = "../publish.gradle.kts")
