package com.samzuhalsetiawan.habbits.ui.composable.delete_background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.samzuhalsetiawan.habbits.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
   swipeDismissState: SwipeToDismissBoxState
) {
   val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
      colorResource(id = R.color.customRed)
   } else {
      Color.Transparent
   }

   Box(
      modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(16.dp),
      contentAlignment = Alignment.CenterEnd
   ) {
      Icon(
         imageVector = Icons.Default.Delete,
         contentDescription = "Delete",
         tint = Color.White
      )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
   item: T,
   onDelete: (T) -> Unit,
   content: @Composable (T) -> Unit
) {

   val state = rememberSwipeToDismissBoxState(
      confirmValueChange = { dismissValue ->
         if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
            onDelete(item)
         }
         false
      }
   )

   SwipeToDismissBox(
      state = state,
      backgroundContent = {
         DeleteBackground(swipeDismissState = state)
      },
      enableDismissFromStartToEnd = false
   ) {
      content(item)
   }

}