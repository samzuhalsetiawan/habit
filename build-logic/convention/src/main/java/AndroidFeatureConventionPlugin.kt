import com.wahyusembiring.habit.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

   override fun apply(target: Project) {
      with(target) {
         with(pluginManager) {
            apply("habit.android.library")
            apply("habit.dagger.hilt")
         }

         dependencies {
            add("implementation", project(":core:ui"))
         }
      }
   }
}