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
  implementation(Libs.kotlin)
  implementation(Libs.coroutines)
  implementation(Libs.dagger)
  kapt(Libs.daggerCompiler)
  implementation(Libs.timber)
}