plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("publish")
}

group = requireNotNull(libs.com.kfaraj.support.appcompat.get().group)
version = requireNotNull(libs.com.kfaraj.support.appcompat.get().version)

android {
    namespace = "com.kfaraj.support.appcompat"
    compileSdk = 36
    defaultConfig {
        minSdk = 23
        aarMetadata {
            minCompileSdk = 36
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    api(libs.androidx.appcompat)
}

pom {
    name = "AppCompat"
    description = "Additional support for the the AndroidX AppCompat library"
    url = "https://gitlab.com/kfaraj/support/-/tree/main/appcompat"
}
