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
            buildConfigField(
                "String",
                "FIREBASE_DATABASE_URL",
                System.getenv()["FIREBASE_DATABASE_URL"] ?: project.property("FIREBASE_DATABASE_URL") as String
            )
        }
        getByName("debug") {
            buildConfigField(
                "String",
                "FIREBASE_DATABASE_URL",
                System.getenv()["FIREBASE_DATABASE_URL"] ?: project.property("FIREBASE_DATABASE_URL") as String
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":core-db-api"))
    implementation(project(":core-db-entity"))
    implementation(project(":module-injector"))
    implementation(project(":core-di"))

    implementation(Libs.kotlin)
    implementation(Libs.coroutines)
    implementation(Libs.room)
    kapt(Libs.roomCompiler)
    implementation(Libs.roomKtx)
    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)
    implementation(Libs.firebaseDb)
}