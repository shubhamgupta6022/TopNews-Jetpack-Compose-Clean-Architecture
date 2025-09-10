package com.sgupta.core.theme.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sgupta.feature.core.R

val loraFontFamily = FontFamily(
    Font(resId = R.font.lora_regular, weight = FontWeight.Normal),
    Font(resId = R.font.lora_bold, weight = FontWeight.Bold)
)