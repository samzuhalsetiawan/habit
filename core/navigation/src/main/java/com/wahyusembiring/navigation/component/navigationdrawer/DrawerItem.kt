package com.wahyusembiring.navigation.component.navigationdrawer

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wahyusembiring.ui.R
import com.wahyusembiring.ui.util.UIText

data class DrawerItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int? = null,
    val category: Category
) {
    enum class Category {
        CATEGORY_1, CATEGORY_2, CATEGORY_3
    }

    companion object {
        val defaultItems: List<DrawerItem>
            get() = listOf(
                DrawerItem(
                    title = R.string.home,
                    icon = R.drawable.ic_home,
                    category = Category.CATEGORY_1
                ),
                DrawerItem(
                    title = R.string.kanban,
                    icon = R.drawable.ic_kanban,
                    category = Category.CATEGORY_1
                ),
                DrawerItem(
                    title = R.string.calendar,
                    icon = R.drawable.ic_calendar,
                    category = Category.CATEGORY_1
                ),
                DrawerItem(
                    title = R.string.timetable,
                    icon = R.drawable.ic_timetable,
                    category = Category.CATEGORY_1
                ),
                DrawerItem(
                    title = R.string.grades,
                    icon = R.drawable.ic_grades,
                    category = Category.CATEGORY_2
                ),
                DrawerItem(
                    title = R.string.subjects,
                    icon = R.drawable.ic_subjects,
                    category = Category.CATEGORY_2
                ),
                DrawerItem(
                    title = R.string.attendance,
                    icon = R.drawable.ic_attendance,
                    category = Category.CATEGORY_2
                ),
                DrawerItem(
                    title = R.string.lectures,
                    icon = R.drawable.ic_teachers,
                    category = Category.CATEGORY_2
                ),
                DrawerItem(
                    title = R.string.recordings,
                    icon = R.drawable.ic_recordings,
                    category = Category.CATEGORY_2
                ),
                DrawerItem(
                    title = R.string.information,
                    icon = R.drawable.ic_info,
                    category = Category.CATEGORY_3
                ),
                DrawerItem(
                    title = R.string.settings,
                    icon = R.drawable.ic_settings,
                    category = Category.CATEGORY_3
                )
            )
    }
}