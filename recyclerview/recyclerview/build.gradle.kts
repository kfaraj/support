plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.com.kfaraj.support.publish)
}

group = requireNotNull(libs.com.kfaraj.support.recyclerview.get().group)
version = requireNotNull(libs.com.kfaraj.support.recyclerview.get().version)

android {
    namespace = "com.kfaraj.support.recyclerview"
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kotlin {
    jvmToolchain(21)
    explicitApi()
}

dependencies {
    api(libs.androidx.recyclerview)
    testImplementation(libs.androidx.test.core.ktx)
    testImplementation(libs.androidx.test.ext.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.org.robolectric)
}

pom {
    name = "RecyclerView"
    description = "Additional support for the the AndroidX RecyclerView library"
    url = "https://github.com/kfaraj/support/tree/main/recyclerview"
}
