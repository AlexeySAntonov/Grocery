plugins {
  id("com.android.library")
  id("kotlin-android")
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
  api(Libs.junit)
  api(Libs.mockk)
  api(Libs.coroutinesTest) {
    // Conflicts with mockk due to direct inclusion of byte buddy
    exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
  }
}