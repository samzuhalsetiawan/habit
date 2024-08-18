plugins {
   alias(libs.plugins.habit.android.library)
   alias(libs.plugins.habit.android.compose)
   alias(libs.plugins.habit.dagger.hilt)
   alias(libs.plugins.habit.testing)
}

android {
   namespace = "com.wahyusembiring.overview"
}

dependencies {
   implementation(project(":core:ui"))
   implementation(project(":core:common"))
   implementation(project(":core:data"))
}