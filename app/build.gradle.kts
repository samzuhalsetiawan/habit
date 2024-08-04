plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.jetbrains.kotlin.android)
   alias(libs.plugins.kotlin.ksp)
   alias(libs.plugins.room.plugin)
   alias(libs.plugins.compose.compiler)
   alias(libs.plugins.kotlin.serialization)
   alias(libs.plugins.kotlin.parcelize)
   alias(libs.plugins.hilt.android.plugin)
}

android {
   namespace = "com.wahyusembiring.habit"
   compileSdk = 34

   defaultConfig {
      applicationId = "com.wahyusembiring.habit"
      minSdk = 24
      targetSdk = 34
      versionCode = 1
      versionName = "1.0"

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      vectorDrawables {
         useSupportLibrary = true
      }
   }

   buildTypes {
      release {
         isMinifyEnabled = false
         proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
         )
      }
   }
   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
   }
   kotlinOptions {
      jvmTarget = "1.8"
   }
   buildFeatures {
      compose = true
   }
   composeOptions {
      kotlinCompilerExtensionVersion = "1.5.1"
   }
   packaging {
      resources {
         excludes += "/META-INF/{AL2.0,LGPL2.1}"
      }
   }
   room {
      schemaDirectory("$projectDir/schemas")
   }
}

dependencies {

   implementation(libs.androidx.core.ktx)
   implementation(libs.androidx.lifecycle.runtime.ktx)
   implementation(libs.androidx.activity.compose)
   implementation(platform(libs.androidx.compose.bom))
   implementation(libs.androidx.ui)
   implementation(libs.androidx.ui.graphics)
   implementation(libs.androidx.ui.tooling.preview)
   implementation(libs.androidx.material3)
   implementation(libs.androidx.ui.text.google.fonts)
   testImplementation(libs.junit)
   androidTestImplementation(libs.androidx.junit)
   androidTestImplementation(libs.androidx.espresso.core)
   androidTestImplementation(platform(libs.androidx.compose.bom))
   androidTestImplementation(libs.androidx.ui.test.junit4)
   debugImplementation(libs.androidx.ui.tooling)
   debugImplementation(libs.androidx.ui.test.manifest)

//   Room
   implementation(libs.room.runtime)
   implementation(libs.room.ktx)
   ksp(libs.room.compiler)

//   Navigation
   implementation(libs.navigation.compose)

//   Lifecycle
   implementation(libs.lifecycle.viewmodel)
   implementation(libs.lifecycle.viewmodel.compose)
   implementation(libs.lifecycle.viewmodel.savedstate)
   ksp(libs.lifecycle.compiler)

//   Kotlin Serialization
   implementation(libs.kotlin.serialization.json)

//   Coil image loader
   implementation(libs.coil.compose)

//   Compose Color Picker
   implementation(libs.compose.colorpicker)

//   boguszpawlowski compose calendar
   implementation(libs.boguszpawlowski.compose.calendar)
   implementation(libs.boguszpawlowski.kotlix.datetime)

   // Dagger Hilt
   implementation(libs.hilt.android)
   implementation(libs.hilt.navigation.compose)
   ksp(libs.hilt.android.compiler)
}