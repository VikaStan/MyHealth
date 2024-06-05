package com.example.myhealth

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myhealth.Screens.MainScreen
import com.example.myhealth.models.MainScreenViewModel
import com.example.myhealth.ui.theme.MyHealthTheme
import dagger.hilt.android.AndroidEntryPoint

class PersonViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(application) as T
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        setContent {
            MyHealthTheme {



                Box(Modifier.safeDrawingPadding()){
                    val owner = LocalViewModelStoreOwner.current
                    owner?.let {
                    val viewModel: MainScreenViewModel = viewModel(
                        it,
                        "UserViewModel",
                        PersonViewModelFactory(LocalContext.current.applicationContext as Application)
                    )
                    MainScreen(mainViewModel = viewModel) }}
                }
            }
        }
    }

