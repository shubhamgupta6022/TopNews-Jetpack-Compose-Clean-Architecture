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
import com.sgupta.analytics.constants.AnalyticsEvents
import com.sgupta.analytics.constants.AnalyticsScreens
import com.sgupta.analytics.extensions.TrackScreenView
import com.sgupta.analytics.extensions.logButtonClick
import com.sgupta.analytics.manager.AnalyticsManager
import com.sgupta.feature.composite.R
import com.sgupta.composite.aiassistant.components.ChatBubble
import com.sgupta.composite.aiassistant.states.AIAssistantBottomSheetViewState
import com.sgupta.core.theme.typography.Typography
import com.sgupta.core.theme.color.colorGreyLightBorder
import com.sgupta.core.theme.color.colorPrimaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAssistantBottomSheet(
    sheetState: SheetState,
    aIAssistantBottomSheetViewState: AIAssistantBottomSheetViewState,
    onDismiss: () -> Unit,
    sendMessageClicked: (String) -> Unit,
    analyticsManager: AnalyticsManager
) {
    var userInput by remember { mutableStateOf("") }
    val chatMessages = aIAssistantBottomSheetViewState.aiAssistantChatUiModel
    
    // Track AI Assistant bottom sheet view
    analyticsManager.TrackScreenView(
        screenName = AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET,
        additionalProperties = mapOf(
            "conversation_messages_count" to (chatMessages?.size ?: 0),
            "is_loading" to aIAssistantBottomSheetViewState.loading
        )
    )

    ModalBottomSheet(
        onDismissRequest = {
            analyticsManager.logEvent(
                com.sgupta.analytics.builder.AnalyticsEventBuilder()
                    .setScreenName(AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET)
                    .setEventType(com.sgupta.analytics.model.EventType.CUSTOM)
                    .setEventName(AnalyticsEvents.AI_ASSISTANT_CLOSED)
                    .addParameter("dismiss_method", "gesture")
            )
            onDismiss()
        },
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
                            analyticsManager.logButtonClick(
                                screenName = AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET,
                                buttonName = "send_message",
                                buttonType = "icon_button",
                                additionalProperties = mapOf(
                                    "message_length" to userInput.length,
                                    "conversation_turn" to (chatMessages?.size ?: 0)
                                )
                            )
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