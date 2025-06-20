package com.sgupta.composite.bottombarnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import com.sgupta.core.theme.color.colorGrey700
import com.sgupta.core.theme.color.colorPrimaryWhite
import com.sgupta.core.theme.color.colorSecondaryInfoBlue
import com.sgupta.navigation.Navigator
import com.sgupta.navigation.destinations.Home
import com.sgupta.navigation.destinations.Reels

val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = Home
    ),
    NavigationItem(
        title = "Profile",
        icon = Icons.Default.Person,
        route = Reels
    )
)

@Composable
fun BottomNavigationBar(navigator: Navigator) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navigator.navigateTo(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue)
                            colorSecondaryInfoBlue
                        else colorGrey700
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorPrimaryWhite,
                    indicatorColor = colorSecondaryInfoBlue
                )
            )
        }
    }
}