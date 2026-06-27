plugins {
    alias(libs.plugins.com.android.application)
}

android {
    namespace = "com.kfaraj.support.recyclerview.samples"
    compileSdk = 37
    defaultConfig {
        applicationId = "com.kfaraj.support.recyclerview.samples"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }
    signingConfigs {
        register("release") {
            storeFile = findProperty("signingStoreFile")?.let { file(it) }
            storePassword = findProperty("signingStorePassword") as? String
            keyAlias = findProperty("signingKeyAlias") as? String
            keyPassword = findProperty("signingKeyPassword") as? String
        }
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":recyclerview:recyclerview"))
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.core)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.com.google.android.material)
}
