package com.sgupta.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sgupta.core.theme.Typography

@Composable
fun SectionHeadline(modifier: Modifier = Modifier, heading: String) {
    Text(
        text = heading,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp),
        textAlign = TextAlign.Start,
        style = Typography.headlineMedium
    )
}