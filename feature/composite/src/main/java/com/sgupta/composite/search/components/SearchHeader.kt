package com.sgupta.composite.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.core.components.SearchBar

@Composable
fun SearchHeader(
    onBackClick: () -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    onSearch: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(id = com.sgupta.core.R.drawable.ic_arrow_back),
                contentDescription = "Back"
            )
        }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            focusRequester = focusRequester,
            onSearch = {
                onSearch(it)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchHeaderPreview() {
    SearchHeader(onBackClick = {})
}