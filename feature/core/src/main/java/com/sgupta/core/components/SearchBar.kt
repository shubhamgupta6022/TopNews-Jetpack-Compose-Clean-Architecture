package com.sgupta.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgupta.core.theme.LightColors

@Composable
fun SearchBar(modifier: Modifier) {
    var query by remember { mutableStateOf("") } // Ensure correct state management

    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                color = LightColors.background, // Make sure LightColors.background is defined
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = Color.Gray
            )
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search for news",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField() // Properly call the inner text field
                }
            )
        }
    }
}
