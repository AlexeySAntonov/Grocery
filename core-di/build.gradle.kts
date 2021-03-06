plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
    }

    buildTypes {
        getByName("release") {
            consumerProguardFile("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":feature-trolleylist-api"))
    implementation(project(":feature-trolleydetails-api"))
    implementation(project(":module-injector"))
    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
}