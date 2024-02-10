package com.vindove.pos.savesoul.ui

import androidx.compose.runtime.Composable
import com.vindove.pos.savesoul.ui.emergency.EmergencyScreen
import com.vindove.pos.savesoul.ui.home.HomeScreen

interface SaveSoulScreens {
    val label: String
    val screen: @Composable () -> Unit
}

object HomeScreens : SaveSoulScreens {
    override val label: String = "Emergency Contacts"
    override val screen: @Composable () -> Unit = { HomeScreen() }
}


object EmergencyScreens : SaveSoulScreens {
    override val label: String
        get() = "Report a new Emergency case"
    override val screen: @Composable () -> Unit
        get() = { EmergencyScreen() }
}