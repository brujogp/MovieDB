plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.soyjoctan.moviedb.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.soyjoctan.moviedb.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            versionNameSuffix = "release"
            isMinifyEnabled = false
        }
        getByName("debug") {
            versionNameSuffix = "test"
            isDebuggable = true
        }
    }
}

dependencies {
    val navVersion = "2.5.3"
    val composeComponent = "1.3.0"
    val hiltVersion = "2.44"
    val coliVersion = "2.2.2"
    val accompanistVersion = "0.27.0"

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:$composeComponent")
    implementation("androidx.compose.ui:ui-tooling:$composeComponent")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeComponent")
    implementation("androidx.compose.foundation:foundation:$composeComponent")
    implementation("androidx.compose.material:material:$composeComponent")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0-alpha03")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeComponent")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("io.coil-kt:coil-compose:$coliVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation ("com.google.android.exoplayer:exoplayer:2.18.2")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

}

kapt {
    correctErrorTypes = true
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}