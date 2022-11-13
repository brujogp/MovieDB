plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    id("com.google.dagger.hilt.android").version("2.44").apply(false)
    id("org.jetbrains.kotlin.plugin.serialization").version("1.7.10")
    id("com.squareup.sqldelight").version("1.5.3").apply(false)
}

buildscript {
    val sqlDelightVersion = "1.5.4"

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}