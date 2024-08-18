plugins {
   alias(libs.plugins.habit.android.library)
   alias(libs.plugins.habit.android.compose)
   alias(libs.plugins.habit.android.navigation)
   alias(libs.plugins.habit.testing)
}

android {
   namespace = "com.wahyusembiring.navigation"
}

dependencies {
   implementation(project(":core:common"))
   implementation(project(":core:ui"))
}