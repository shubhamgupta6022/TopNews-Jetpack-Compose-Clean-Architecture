package com.sgupta.core.components.toolbar.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sgupta.core.theme.color.colorPrimaryBlack

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        CircularProgressIndicator(
            color = colorPrimaryBlack,
            strokeWidth = 4.dp,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}