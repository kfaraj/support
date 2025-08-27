plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.kfaraj.support.publish)
}

group = requireNotNull(libs.com.kfaraj.support.recyclerview.get().group)
version = requireNotNull(libs.com.kfaraj.support.recyclerview.get().version)

android {
    namespace = "com.kfaraj.support.recyclerview"
    compileSdk = 36
    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    testOptions {
        targetSdk = 36
        managedDevices {
            localDevices {
                register("mediumPhoneApi36") {
                    device = "Medium Phone"
                    apiLevel = 36
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
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}

pom {
    name = "RecyclerView"
    description = "Additional support for the the AndroidX RecyclerView library"
    url = "https://gitlab.com/kfaraj/support/-/tree/main/recyclerview"
}
