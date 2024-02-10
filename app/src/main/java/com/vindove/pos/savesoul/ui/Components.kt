package com.vindove.pos.savesoul.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vindove.pos.savesoul.domain.model.EmergencyContact
import com.vindove.pos.savesoul.ui.home.userActions

@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFloatingStateChanged: (MultiFloatingState) -> Unit,
    onMinFabItemClicked: (MinFabItem) -> Unit
) {
    val transition =
        updateTransition(targetState = multiFloatingState, label = "floatingButtonAnimation")

    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    Column(horizontalAlignment = Alignment.End) {
        if (transition.currentState == MultiFloatingState.Expanded) {
            userActions.forEach {
                MinFab(item = it, onMinFabItemClicked = {
                    onMinFabItemClicked(it)
                })
            }
        }
        FloatingActionButton(
            onClick = {
                onMultiFloatingStateChanged(
                    if (transition.currentState == MultiFloatingState.Expanded) {
                        MultiFloatingState.Collapsed
                    } else MultiFloatingState.Expanded
                )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.rotate(rotate)
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun EmergencyContactCard(modifier: Modifier = Modifier,emergencyContact: EmergencyContact = EmergencyContact()) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Color.Red)
            .padding(8.dp)
    ) {
        Text(
            text = emergencyContact.contactName.first().toString(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(6.dp)
        )
        Text(text = emergencyContact.contactName)
        Text(text = emergencyContact.contactNumber)
    }
}


@Composable
fun MinFab(
    modifier: Modifier = Modifier,
    item: MinFabItem,
    onMinFabItemClicked: (MinFabItem) -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier.padding(8.dp),
        onClick = { onMinFabItemClicked.invoke(item) },
        icon = { Icon(imageVector = item.icon, contentDescription = item.identifier) },
        text = { Text(text = item.label) }
    )
}

enum class MultiFloatingState {
    Expanded, Collapsed
}


data class MinFabItem(
    val icon: ImageVector,
    val label: String,
    val identifier: String
)

