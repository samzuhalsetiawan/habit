package com

import com.android.build.api.dsl.LibraryExtension
import com.wahyusembiring.habit.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {

   override fun apply(target: Project) {
      with(target) {
         with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
         }

         extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig {
               consumerProguardFiles("consumer-rules.pro")
            }
         }

         dependencies {
            add("androidTestImplementation", kotlin("test"))
            add("testImplementation", kotlin("test"))
         }
      }
   }
}