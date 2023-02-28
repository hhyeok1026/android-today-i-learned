import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

plugins {
    id("com.android.application")
}

androidExtensions {
    experimental = true
}


android {
    namespace = "com.example.kotlindsl"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.kotlindsl"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            // minifyEnabled false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}