plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

group = "com.kfaraj.support"
version = "4.0.0-SNAPSHOT"

android {
    namespace = "com.kfaraj.support.recyclerview"
    compileSdk = 34
    defaultConfig {
        minSdk = 19
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    testOptions {
        targetSdk = 34
        managedDevices {
            localDevices {
                register("pixel8Api34") {
                    device = "Pixel 8"
                    apiLevel = 34
                    systemImageSource = "aosp-atd"
                }
            }
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(libs.androidx.recyclerview)
    debugImplementation(libs.androidx.core.ktx)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}

extra["pomName"] = "RecyclerView"
extra["pomDescription"] = "This library is built on top of the AndroidX RecyclerView library."
extra["pomUrl"] = "https://gitlab.com/kfaraj/support#recyclerview"

apply(from = "../publish.gradle.kts")
