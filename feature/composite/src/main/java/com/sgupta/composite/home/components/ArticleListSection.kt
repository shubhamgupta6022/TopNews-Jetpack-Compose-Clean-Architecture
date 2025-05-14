package com.sgupta.composite.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sgupta.core.theme.Typography
import com.sgupta.domain.model.ArticleDataModel
import com.sgupta.domain.model.SourceDataModel

private val titleStyle = Typography.titleMedium.copy(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold
)

private val descriptionStyle = Typography.bodyMedium.copy(fontSize = 14.sp)

@Composable
fun ArticleListItem(
    articleDataModel: ArticleDataModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageRequest = remember(articleDataModel.urlToImage) {
        ImageRequest.Builder(context)
            .data(articleDataModel.urlToImage)
            .crossfade(true)
            .size(100, 100)
            .build()
    }
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        content = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = articleDataModel.title.orEmpty(),
                        style = titleStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = articleDataModel.description.orEmpty(),
                        style = descriptionStyle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun ArticleListItemPreview() {
    ArticleListItem(
        ArticleDataModel(
            source = SourceDataModel(),
            title = "Test preview",
            description = "Preview Description",

        )
    )
}