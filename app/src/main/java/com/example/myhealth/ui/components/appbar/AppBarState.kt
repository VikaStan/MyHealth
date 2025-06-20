package com.example.myhealth.ui.components.appbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myhealth.screens.Screen
import com.example.myhealth.screens.Screen.Account.getScreen

@Stable
// 1
class AppBarState(
    val navController: NavController,
) {
    // 2
    private val currentScreenRoute: String?
        @Composable get() {
            var str: String? = navController.currentBackStackEntryAsState().value?.destination?.route
            if (str != null) {
                str = if (navController.currentBackStackEntryAsState().value?.destination?.route?.contains("/") == true) {
                        str.substring(0, str.indexOf('/')
                        )
                    } else navController.currentBackStackEntryAsState().value?.destination?.route
            }
            return str
        }

    // 3
    val currentScreen: Screen?
        @Composable get() = getScreen(currentScreenRoute)

    // 4
    val isVisible: Boolean
        @Composable get() = currentScreen?.isAppBarVisible == true

    val navigationIcon: ImageVector?
        @Composable get() = currentScreen?.navigationIcon

    val navigationIconContentDescription: String?
        @Composable get() = currentScreen?.navigationIconContentDescription

    val onNavigationIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.onNavigationIconClick

    val title: Int?
        @Composable get() = currentScreen?.title

    val actions: List<ActionMenuItem>
        @Composable get() = currentScreen?.actions.orEmpty()
}

// 5
@Composable
fun rememberAppBarState(
    navController: NavController,
) = remember { AppBarState(navController) }