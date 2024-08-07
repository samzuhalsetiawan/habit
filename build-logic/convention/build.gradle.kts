import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
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
   compileOnly(libs.android.tools.common)
   compileOnly(libs.compose.gradlePlugin)
   compileOnly(libs.kotlin.gradlePlugin)
   compileOnly(libs.ksp.gradlePlugin)
   compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
   plugins {
      register("androidApplicationCompose") {
         id = "habit.android.application.compose"
         implementationClass = "AndroidApplicationComposeConventionPlugin"
      }
      register("androidApplication") {
         id = "habit.android.application"
         implementationClass = "AndroidApplicationConventionPlugin"
      }
      register("androidLibraryCompose") {
         id = "habit.android.library.compose"
         implementationClass = "AndroidLibraryComposeConventionPlugin"
      }
      register("androidLibrary") {
         id = "habit.android.library"
         implementationClass = "AndroidLibraryConventionPlugin"
      }
      register("daggerHilt") {
         id = "habit.dagger.hilt"
         implementationClass = "DaggerHiltConventionPlugin"
      }
      register("androidRoom") {
         id = "habit.android.room"
         implementationClass = "AndroidRoomConventionPlugin"
      }
      register("androidFeature") {
         id = "habit.android.feature"
         implementationClass = "AndroidFeatureConventionPlugin"
      }
   }
}