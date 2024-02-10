package com.vindove.pos.savesoul.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vindove.pos.savesoul.R
import com.vindove.pos.savesoul.ui.EmergencyContactCard
import com.vindove.pos.savesoul.ui.MinFabItem


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val emergencyContacts by viewModel.emergencyContact.collectAsState()
    LazyVerticalGrid(
        modifier = modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(emergencyContacts) { item ->
            EmergencyContactCard()
        }
    }
}


val userActions = listOf(
    MinFabItem(
        icon = Icons.Default.AccountBox,
        label = "Add a new Emergency Number",
        identifier = "New Number"
    ),

    MinFabItem(
        icon = Icons.Default.Warning,
        label = "Report a new Emergency",
        identifier = "New Emergency"
    ),
)