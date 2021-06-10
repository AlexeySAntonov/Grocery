object Versions {
    const val buildTools = "30.0.2"
    const val compileSdk = 30
    const val targetSdk = 30
    const val minSdk = 21

    const val kotlin = "1.4.32"
    const val coroutines = "1.4.0"
    const val dagger = "2.28"
    const val room = "2.2.5"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:1.5.0"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val threeten = "com.jakewharton.threetenabp:threetenabp:1.2.4"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val fragment = "androidx.fragment:fragment-ktx:1.2.4"
    const val appCompat = "androidx.appcompat:appcompat:1.3.0"
    const val material = "com.google.android.material:material:1.3.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val delegates = "com.hannesdorfmann:adapterdelegates4:4.3.0"

    // Database
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    // Firebase
    const val firebaseDb = "com.google.firebase:firebase-database-ktx:20.0.0"
}

object BuildPlugins {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val AGP = "com.android.tools.build:gradle:4.2.1"
    const val googleServices = "com.google.gms:google-services:4.3.8"
}