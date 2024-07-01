plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

group = "com.kfaraj.support.appcompat"
version = "5.0.0"

android {
    namespace = "com.kfaraj.support.appcompat"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
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
extra["pomDescription"] = "Provides additional support for the the AndroidX AppCompat library."
extra["pomUrl"] = "https://gitlab.com/kfaraj/support/-/tree/main/appcompat"

apply(from = "../../publish.gradle.kts")
