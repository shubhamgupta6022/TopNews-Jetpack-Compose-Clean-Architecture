package com.sgupta.composite.aiassistant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sgupta.composite.R
import com.sgupta.composite.aiassistant.components.ChatBubble
import com.sgupta.composite.aiassistant.states.AIAssistantBottomSheetViewState
import com.sgupta.core.theme.Typography
import com.sgupta.core.theme.colorGreyLightBorder
import com.sgupta.core.theme.colorPrimaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAssistantBottomSheet(
    sheetState: SheetState,
    aIAssistantBottomSheetViewState: AIAssistantBottomSheetViewState,
    onDismiss: () -> Unit,
    sendMessageClicked: (String) -> Unit
) {
    var userInput by remember { mutableStateOf("") }
    val chatMessages = aIAssistantBottomSheetViewState.aiAssistantChatUiModel

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(0.85f)
                .padding(16.dp)
        ) {
            Text(
                text = "AI Assistant",
                modifier = Modifier.padding(bottom = 8.dp),
                style = Typography.titleMedium
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                chatMessages?.size?.let {
                    items(it) {
                        ChatBubble(chatMessages[it])
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(colorPrimaryWhite, shape = RoundedCornerShape(16.dp))
                        .border(1.dp, colorGreyLightBorder, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        placeholder = { Text("Ask something...") },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            containerColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)) // Space between TextField and Button

                IconButton(
                    onClick = {
                        if (userInput.isNotBlank()) {
                            sendMessageClicked(userInput)
                            userInput = ""
                        }
                    },
                    modifier = Modifier.size(48.dp) // Match height of TextField
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_message_send),
                        contentDescription = "Send"
                    )
                }
            }

        }
    }
}