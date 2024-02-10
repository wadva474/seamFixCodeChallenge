package com.vindove.pos.savesoul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vindove.pos.savesoul.ui.EmergencyScreens
import com.vindove.pos.savesoul.ui.HomeScreens
import com.vindove.pos.savesoul.ui.MultiFloatingButton
import com.vindove.pos.savesoul.ui.MultiFloatingState
import com.vindove.pos.savesoul.ui.emergency.EmergencyScreen
import com.vindove.pos.savesoul.ui.home.HomeScreen
import com.vindove.pos.savesoul.ui.theme.SaveSoulTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaveSoulTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainApp(navController)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(navController: NavHostController) {

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    var mutableFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }
    Scaffold(
        floatingActionButton = {
            if (currentDestination?.route == HomeScreens.label) {
                MultiFloatingButton(
                    multiFloatingState = mutableFloatingState,
                    onMultiFloatingStateChanged = {
                        mutableFloatingState = it
                    },
                    onMinFabItemClicked = {
                        when (it.identifier) {
                            "New Emergency" -> {
                                navController.navigate(EmergencyScreens.label)
                            }
                        }
                    }
                )
            }
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text= if (currentDestination?.route == HomeScreens.label) HomeScreens.label else EmergencyScreens.label)
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeScreens.label,
            modifier = Modifier.padding(it)
        ) {
            composable(HomeScreens.label) {
                HomeScreens.screen.invoke()
            }

            composable(EmergencyScreens.label) {
                EmergencyScreen(
                    onSuccessfullyPosted = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
