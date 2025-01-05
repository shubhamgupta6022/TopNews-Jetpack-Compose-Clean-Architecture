package com.sgupta.composite.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.core.theme.Typography

@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    val typography = remember { Typography }
    Column(modifier = modifier) {
        Text(
            text = title,
            style = typography.headlineMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun SectionHeaderPreview() {
    SectionHeader("Countries", Modifier)
}