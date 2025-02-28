package com.sgupta.composite.home.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.composite.R
import com.sgupta.core.theme.colorSecondaryInfoBlue

@Composable
fun ChatFloatingActionBtn(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        containerColor = colorSecondaryInfoBlue,
        shape = CircleShape, // Ensures it's circular
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chat_fab), // Replace with your drawable
            contentDescription = "Add",
            tint = Color.White // Change color of the icon
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChatFloatingActionBtnPreview(modifier: Modifier = Modifier) {
    ChatFloatingActionBtn(modifier) {

    }
}