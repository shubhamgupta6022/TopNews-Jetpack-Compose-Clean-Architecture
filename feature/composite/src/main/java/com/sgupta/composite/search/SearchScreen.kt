package com.sgupta.composite.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sgupta.composite.search.components.SearchHeader

@Composable
fun SearchScreen() {
    Column(modifier = Modifier.background(color = Color.White)) {
        SearchHeader(onBackClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}