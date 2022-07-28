plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Versions.Sdk.targetSdkVersion

    defaultConfig {
        minSdk = Versions.Sdk.minSdkVersion
        targetSdk = Versions.Sdk.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    Deps.Test.testDependencies.forEach {
        testImplementation(it)
    }
    implementation(project(":api"))
    implementation(project(":contract"))
}