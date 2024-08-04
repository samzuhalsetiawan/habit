import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
//   id("java-library")
//   alias(libs.plugins.jetbrains.kotlin.jvm)
   `kotlin-dsl`
}

group = "com.wahyusembiring.habit.buildlogic"

java {
   sourceCompatibility = JavaVersion.VERSION_17
   targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
   compilerOptions {
      jvmTarget = JvmTarget.JVM_17
   }
}

dependencies {
   compileOnly(libs.android.gradlePlugin)
   compileOnly(libs.kotlin.gradlePlugin)
}