package com.wahyusembiring.navigation.component.navigationdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.wahyusembiring.navigation.Screen
import com.wahyusembiring.ui.R
import com.wahyusembiring.ui.theme.spacing

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigationDrawerState: NavigationDrawerState,
    content: @Composable () -> Unit,
) {
    NavigationDrawer(
        modifier = modifier,
        navigationDrawerState = navigationDrawerState,
        drawerItems = DrawerItem.defaultItems,
        content = content,
    ) {
        when (it.title) {
            R.string.home -> navController.navigate(Screen.Overview)
            R.string.kanban -> navController.navigate(Screen.KanbanBoard)
        }
    }
}

@Composable
private fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navigationDrawerState: NavigationDrawerState,
    drawerItems: List<DrawerItem> = emptyList(),
    content: @Composable () -> Unit,
    onDrawerItemClick: (DrawerItem) -> Unit = {},
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = navigationDrawerState.materialDrawerState,
        gesturesEnabled = navigationDrawerState.slideGestureEnabled,
        drawerContent = {
            NavigationDrawerContent(
                drawerItems = drawerItems,
                onDrawerItemClick = onDrawerItemClick
            )
        },
        content = content
    )
}

@Composable
private fun NavigationDrawerContent(
    drawerItems: List<DrawerItem> = emptyList(),
    onDrawerItemClick: (DrawerItem) -> Unit = {}
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .widthIn(min = 250.dp, max = 300.dp)
    ) {
        DrawerHeader()
        HorizontalDivider()
        DrawerBody(
            drawerItems = drawerItems,
            onDrawerItemClick = onDrawerItemClick
        )
    }
}

@Composable
private fun DrawerHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.Large)
            .padding(top = MaterialTheme.spacing.Large, bottom = MaterialTheme.spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(100)),
            painter = painterResource(id = R.drawable.app_icon),
            contentScale = ContentScale.Crop,
            contentDescription = "App Logo"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Habit",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ColumnScope.DrawerBody(
    drawerItems: List<DrawerItem> = emptyList(),
    onDrawerItemClick: (DrawerItem) -> Unit = {}
) {
    var selectedDrawerItem by remember { mutableStateOf(drawerItems.first()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
        drawerItems.forEachIndexed { index, drawerItem ->
            NavigationDrawerItem(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.Large),
                icon = drawerItem.icon?.let {
                    {
                        Icon(
                            painter = painterResource(id = drawerItem.icon),
                            contentDescription = stringResource(id = drawerItem.title)
                        )
                    }
                },
                label = { Text(text = stringResource(id = drawerItem.title)) },
                selected = selectedDrawerItem == drawerItem,
                onClick = {
                    selectedDrawerItem = drawerItem
                    onDrawerItemClick(drawerItem)
                }
            )
            if (drawerItem != drawerItems.last() && drawerItem.category != drawerItems[index + 1].category) {
                HorizontalDivider()
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
    }
}

@Preview(showBackground = true)
@Composable
private fun MainNavigationDrawerPreview() {
    val drawerItems = DrawerItem.defaultItems
    NavigationDrawerContent(
        drawerItems = drawerItems,
        onDrawerItemClick = {}
    )
}
