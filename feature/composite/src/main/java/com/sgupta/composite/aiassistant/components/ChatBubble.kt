package com.sgupta.composite.aiassistant.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgupta.composite.aiassistant.model.AIAssistantChatUiModel
import com.sgupta.core.theme.Typography
import com.sgupta.core.theme.colorGrey100
import com.sgupta.core.theme.colorPrimaryBlack
import com.sgupta.core.theme.colorPrimaryWhite
import com.sgupta.core.theme.colorSecondaryInfoBlue

@Composable
fun ChatBubble(aiAssistantChatUiModel: AIAssistantChatUiModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // Fetch screen width

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (aiAssistantChatUiModel.isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = screenWidth * 0.7f)
                .background(
                    color = if (aiAssistantChatUiModel.isUser) colorSecondaryInfoBlue else colorGrey100,
                    shape = RoundedCornerShape(
                        topEnd = if (aiAssistantChatUiModel.isUser) 0.dp else 16.dp,
                        topStart = if (aiAssistantChatUiModel.isUser) 16.dp else 0.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = aiAssistantChatUiModel.message,
                color = if (aiAssistantChatUiModel.isUser) colorPrimaryWhite else colorPrimaryBlack,
                style = Typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ChatBubblePreview() {
    ChatBubble(
        AIAssistantChatUiModel(
            message = "Hello! I'm your AI news assistant. How can I help you today?",
            isUser = false
        )
    )
}