package com.sgupta.composite.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sgupta.core.theme.Typography
import com.sgupta.core.theme.colorGrey500
import com.sgupta.core.theme.colorGrey700
import com.sgupta.domain.model.ArticleDataModel

@Composable
fun TopHeadLineSection(topNewsList: List<ArticleDataModel>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(topNewsList.size) {
            TopHeadLineItem(topNewsList[it])
        }
    }
}

@Composable
fun TopHeadLineItem(articleDataModel: ArticleDataModel) {
    val configuration = LocalConfiguration.current
    // Calculate and remember size values
    val cardSize = remember(configuration.screenWidthDp) {
        (configuration.screenWidthDp.dp * 0.40f)
    }

    val imageModifier = remember {
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
    }

    val titleStyle = remember {
        Typography.headlineMedium.copy(fontSize = 18.sp)
    }

    val descriptionStyle = remember {
        Typography.labelSmall.copy(fontSize = 14.sp)
    }

    Card(
        modifier = Modifier
            .width(cardSize)
            .height(cardSize),
        shape = remember { RoundedCornerShape(12.dp) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AsyncImage( // Using AsyncImage instead of Image with rememberAsyncImagePainter
                model = ImageRequest.Builder(LocalContext.current)
                    .data(articleDataModel.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = articleDataModel.description,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = articleDataModel.title.orEmpty(),
                style = titleStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                color = colorGrey700
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = articleDataModel.description.orEmpty(),
                style = descriptionStyle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                color = colorGrey500
            )
        }
    }
}
