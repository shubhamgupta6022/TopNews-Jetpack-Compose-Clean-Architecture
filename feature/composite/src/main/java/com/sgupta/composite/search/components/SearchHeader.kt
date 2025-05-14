package com.sgupta.composite.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.composite.R
import com.sgupta.core.components.SearchBar

@Composable
fun SearchHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        IconButton(
            onClick = {
                onBackClick()
            },
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(id = com.sgupta.core.R.drawable.ic_arrow_back),
                contentDescription = "Menu Icon"
            )
        }
        SearchBar(modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchHeaderPreview() {
    SearchHeader(onBackClick = {})
}