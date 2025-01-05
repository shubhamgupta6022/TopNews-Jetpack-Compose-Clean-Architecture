package com.sgupta.composite.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.sgupta.core.components.ToolbarComposable
import com.sgupta.core.theme.DarkGray
import com.sgupta.core.theme.NeutralBlack
import com.sgupta.core.theme.Typography

@Composable
fun NewsList(navController: NavHostController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    // Handle screen coming to foreground
                    println("ON_RESUME called new screen ")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    // Handle screen going to background
                    println("ON_PAUSE called news screen")
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Column {
        ToolbarComposable(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            navController.popBackStack()
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(count = 5) {
                NewsListItem()
            }
        }
    }
}

@Composable
fun NewsListItem() {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set the background color to white
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp // Adjust the elevation for shadow effect
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://images.unsplash.com/photo-1504674900247-0877df9cc836", // Sample URL
                    contentScale = ContentScale.Crop // Crop the image
                ),
                contentDescription = "Top headline image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Climate Crisis",
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                style = Typography.headlineMedium.copy(fontSize = 18.sp),
                color = NeutralBlack
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Urgent actions needed to combat climate change.",
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                style = Typography.labelSmall.copy(fontSize = 14.sp),
                color = DarkGray
            )
        }
    }
}