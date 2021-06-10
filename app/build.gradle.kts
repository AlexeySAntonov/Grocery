plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("release") {
            keyAlias = System.getenv()["GROCERY_KEY_ALIAS"] ?: project.property("GROCERY_KEY_ALIAS") as String
            keyPassword = System.getenv()["GROCERY_KEY_PASSWORD"] ?: project.property("GROCERY_KEY_PASSWORD") as String
            storeFile = file(System.getenv()["GROCERY_KEYSTORE_PATH"] ?: project.property("GROCERY_KEYSTORE_PATH") as String)
            storePassword = System.getenv()["GROCERY_KEYSTORE_PASSWORD"] ?: project.property("GROCERY_KEYSTORE_PASSWORD") as String
        }
    }

    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId("com.aleksejantonov.grocery")
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = VERSION_CODE
        versionName = VERSION_NAME

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isDebuggable = false
            if (System.getenv()["CI"] == null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":core-db-impl"))
    implementation(project(":core-navigation"))
    implementation(project(":core-di"))
    implementation(project(":module-injector"))
    implementation(project(":feature-trolleylist-impl"))
    implementation(project(":feature-trolleydetails-impl"))

    implementation(Libs.kotlin)
    implementation(Libs.appCompat)
    implementation(Libs.fragment)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.delegates)
    implementation(Libs.ktx)
    implementation(Libs.timber)
    implementation(Libs.coroutines)
    implementation(Libs.threeten)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

}