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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":feature-trolleylist-api"))
    implementation(project(":core-ui-base"))
    implementation(project(":core-db-api"))
    implementation(project(":core-navigation"))
    implementation(project(":module-injector"))
    implementation(project(":core-di"))
    implementation(project(":core-network-util"))

    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
}