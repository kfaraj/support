plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("publish")
}

group = requireNotNull(libs.com.kfaraj.support.recyclerview.get().group)
version = requireNotNull(libs.com.kfaraj.support.recyclerview.get().version)

android {
    namespace = "com.kfaraj.support.recyclerview"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        aarMetadata {
            minCompileSdk = 35
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    testOptions {
        targetSdk = 35
        managedDevices {
            localDevices {
                register("pixel9Api35") {
                    device = "Pixel 9"
                    apiLevel = 35
                    systemImageSource = "aosp-atd"
                }
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    api(libs.androidx.recyclerview)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}

pom {
    name = "RecyclerView"
    description = "Provides additional support for the the AndroidX RecyclerView library."
    url = "https://gitlab.com/kfaraj/support/-/tree/main/recyclerview"
}
