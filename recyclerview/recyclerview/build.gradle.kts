plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

group = "com.kfaraj.support.recyclerview"
version = "4.0.1"

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
    debugImplementation(libs.androidx.core.ktx)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}

extra["pomName"] = "RecyclerView"
extra["pomDescription"] = "Provides additional support for the the AndroidX RecyclerView library."
extra["pomUrl"] = "https://gitlab.com/kfaraj/support/-/tree/main/recyclerview"

apply(from = "../../publish.gradle.kts")
