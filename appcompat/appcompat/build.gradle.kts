plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("publish-convention")
}

group = "com.kfaraj.support.appcompat"
version = "5.0.0-SNAPSHOT"

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

pom {
    name = "AppCompat"
    description = "This library is built on top of the AndroidX AppCompat library."
    url = "https://gitlab.com/kfaraj/support/-/tree/main/appcompat"
}
