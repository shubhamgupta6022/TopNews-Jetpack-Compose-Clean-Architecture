package com.sgupta.core.components.toolbar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgupta.core.R
import com.sgupta.core.components.toolbar.model.ToolbarAction
import com.sgupta.core.theme.color.colorGreyLightBorder
import com.sgupta.core.theme.typography.Typography

// Composable for search bar content
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchBarContent(
    placeholder: String,
    onSearch: (String) -> Unit,
    focusRequester: FocusRequester?
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it)
        },
        placeholder = {
            Text(
                text = placeholder,
                style = Typography.titleSmall.copy(
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = colorGreyLightBorder, // very light gray background like the image
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else Modifier
            ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = Color.Gray,
            )
        }
    )
}

// Composable for title content
@Composable
internal fun TitleContent(
    title: String,
    style: TextStyle,
    textAlign: TextAlign,
    color: Color
) {
    Text(
        text = title,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}

// Composable for toolbar action button
@Composable
internal fun ToolbarActionButton(
    action: ToolbarAction,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = action.onClick,
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = action.icon),
            contentDescription = action.contentDescription,
            tint = if (action.tint != Color.Unspecified) action.tint else contentColor
        )
    }
} 