package com.wahyusembiring.ui.component.navigationdrawer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.ui.R

data class DrawerItem(
   val title: String,
   val icon: Painter? = null,
   val category: Category
) {
   enum class Category {
      CATEGORY_1, CATEGORY_2, CATEGORY_3
   }

   companion object {
      val defaultItems: List<DrawerItem>
         @Composable
         get() = listOf(
            DrawerItem(
               title = stringResource(R.string.home),
               icon = painterResource(id = R.drawable.ic_home),
               category = Category.CATEGORY_1
            ),
            DrawerItem(
               title = stringResource(R.string.agenda),
               icon = painterResource(id = R.drawable.ic_agenda),
               category = Category.CATEGORY_1
            ),
            DrawerItem(
               title = stringResource(R.string.calendar),
               icon = painterResource(id = R.drawable.ic_calendar),
               category = Category.CATEGORY_1
            ),
            DrawerItem(
               title = stringResource(R.string.timetable),
               icon = painterResource(id = R.drawable.ic_timetable),
               category = Category.CATEGORY_1
            ),
            DrawerItem(
               title = stringResource(R.string.grades),
               icon = painterResource(id = R.drawable.ic_grades),
               category = Category.CATEGORY_2
            ),
            DrawerItem(
               title = stringResource(R.string.subjects),
               icon = painterResource(id = R.drawable.ic_subjects),
               category = Category.CATEGORY_2
            ),
            DrawerItem(
               title = stringResource(R.string.attendance),
               icon = painterResource(id = R.drawable.ic_attendance),
               category = Category.CATEGORY_2
            ),
            DrawerItem(
               title = stringResource(R.string.lectures),
               icon = painterResource(id = R.drawable.ic_teachers),
               category = Category.CATEGORY_2
            ),
            DrawerItem(
               title = stringResource(R.string.recordings),
               icon = painterResource(id = R.drawable.ic_recordings),
               category = Category.CATEGORY_2
            ),
            DrawerItem(
               title = stringResource(R.string.information),
               icon = painterResource(id = R.drawable.ic_info),
               category = Category.CATEGORY_3
            ),
            DrawerItem(
               title = stringResource(R.string.settings),
               icon = painterResource(id = R.drawable.ic_settings),
               category = Category.CATEGORY_3
            )
         )
   }
}