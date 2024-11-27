package com.sgupta.topnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.sgupta.composite.NewsHomeScreen
import com.sgupta.domain.TopNewsRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var newsRepo: TopNewsRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenCompose()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenCompose() {
    NewsHomeScreen()
}