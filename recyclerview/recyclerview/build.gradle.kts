plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("publish-convention")
}

group = "com.kfaraj.support.recyclerview"
version = "4.0.0-SNAPSHOT"

android {
    namespace = "com.kfaraj.support.recyclerview"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
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

pom {
    name = "RecyclerView"
    description = "This library is built on top of the AndroidX RecyclerView library."
    url = "https://gitlab.com/kfaraj/support/-/tree/main/recyclerview"
}
