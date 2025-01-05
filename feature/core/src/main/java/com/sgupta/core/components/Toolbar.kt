package com.sgupta.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgupta.core.R
import com.sgupta.core.theme.LightColors
import com.sgupta.core.theme.Typography

@Composable
fun ToolbarComposable(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Menu Icon"
                )
            }

            Text(
                text = "Top News",
                style = Typography.displayLarge.copy(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.Start
            )
        }

        Separator()
    }
}

@Composable
fun Separator() {
    Divider(
        color = LightColors.surface,
        thickness = 1.dp
    )
}