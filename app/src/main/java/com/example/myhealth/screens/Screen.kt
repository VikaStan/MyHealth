package com.example.myhealth.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myhealth.R
import com.example.myhealth.ui.components.appbar.ActionMenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class Screen(
    val route: String,
    val title: Int,
    val buttomIcon: ImageVector,
    val isAppBarVisible: Boolean = true,
    val navigationIcon: ImageVector? = null,
    val navigationIconContentDescription: String? = null,
    var onNavigationIconClick: (() -> Unit)? = null,
    val actions: List<ActionMenuItem> = emptyList()
) {

    object Diary : Screen(
        route = "diary",
        title = R.string.diary_screen,
        buttomIcon = Icons.AutoMirrored.Filled.MenuBook,
        isAppBarVisible = true,
        navigationIcon = Icons.Default.CalendarMonth
    ){
        // 1
        enum class AppBarIcons {
            Calendar
        }
        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        val dialog = mutableStateOf(false)

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.Calendar) }
        }

        fun showDialog(){
            dialog.value = !dialog.value
        }
    }

    object FoodAdd : Screen(
        route = "food_add",
        title = R.string.food_add,
        buttomIcon = Icons.AutoMirrored.Filled.MenuBook,
        isAppBarVisible = true,
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
    ){
        // 1
        enum class AppBarIcons {
            Back,

        }

        val dialog = mutableStateOf(false)
        fun showDialog(){
            dialog.value = !dialog.value
        }
        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.Back) }
        }
    }

    object SleepAdd : Screen(
        route = "sleep_add",
        title = R.string.sleep_title,
        buttomIcon = Icons.AutoMirrored.Filled.MenuBook,
        isAppBarVisible = true,
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
    ){
        // 1
        enum class AppBarIcons {
            Back,
        }

        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.Back) }
        }
    }

    object Stats : Screen(
        route = "stats",
        title = R.string.stats_screen,
        buttomIcon = Icons.Default.Equalizer,
        isAppBarVisible = true,
        navigationIcon = Icons.Default.CalendarMonth
    ){
        // 1
        enum class AppBarIcons {
            Calendar
        }

        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.Calendar) }
        }


        val dialog = mutableStateOf(false)

        fun showDialog(){
            dialog.value = !dialog.value
        }
    }
    object Account : Screen(
        route = "account",
        title = R.string.account_screen,
        buttomIcon = Icons.Default.Person,
        isAppBarVisible = true,
        navigationIcon = Icons.Default.Settings,
        navigationIconContentDescription = null,
        actions = emptyList()
    ){
        // 1
        enum class AppBarIcons {
            Settings
        }

        // 2
        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.Settings) }
        }
    }

    object Settings : Screen(
        route = "settings",
        title = R.string.settings_screen,
        buttomIcon = Icons.Default.Person,
        isAppBarVisible = true,
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
    )
    {
        enum class AppBarIcons {
            NavigationIcon
        }

        private val _buttons = MutableSharedFlow<AppBarIcons>(extraBufferCapacity = 1)
        val buttons: Flow<AppBarIcons> = _buttons.asSharedFlow()

        init {
            onNavigationIconClick = { _buttons.tryEmit(AppBarIcons.NavigationIcon) }
        }
    }

    object Data : Screen(
        route = "calendar",
        title = R.string.data,
        buttomIcon = Icons.Default.CalendarMonth,
        isAppBarVisible = false,
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationIconClick = null,
        navigationIconContentDescription = null,
        actions = emptyList()
    )

    object Onboarding : Screen(
        route = "onboarding",
        title = R.string.app_name,
        buttomIcon = Icons.Default.Home,
        isAppBarVisible = false,
        navigationIcon = null,
        onNavigationIconClick = null,
        navigationIconContentDescription = null,
        actions = emptyList()
    )

    object Dashboard : Screen(
        route = "dashboard",
        title = R.string.dashboard_screen,
        buttomIcon = Icons.Default.Home,
        isAppBarVisible = false,
        navigationIcon = null,
        onNavigationIconClick = null,
        navigationIconContentDescription = null,
        actions = emptyList()
    )


    fun getScreen(route: String?): Screen? = Screen::class.nestedClasses.map {
            kClass -> kClass.objectInstance as Screen
    }.firstOrNull { screen -> screen.route == route }
}


