package com.wahyusembiring.habit.domain.utils

import kotlin.reflect.KMutableProperty0

object CreationUtil {

   /**
    * Create a single instance of an object and store it in [volatileInstanceProp] property.
    *
    * @param volatileInstanceProp property to store the created instance
    * @param initializer a lambda function to create the instance
    *
    * @return [T] the created instance or the existing instance if it already exists
    * */
   fun <T> createSingleton(
      volatileInstanceProp: KMutableProperty0<T?>,
      initializer: () -> T
   ): T {
      return volatileInstanceProp.get() ?: synchronized(this) {
         volatileInstanceProp.get() ?: initializer().also {
            volatileInstanceProp.set(it)
         }
      }
   }

}