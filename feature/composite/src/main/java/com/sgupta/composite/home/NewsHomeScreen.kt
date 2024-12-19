package com.sgupta.composite.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgupta.composite.R
import com.sgupta.composite.home.components.CategoriesSectionItem
import com.sgupta.composite.home.components.CountriesSectionItem
import com.sgupta.composite.home.components.NewsListItem
import com.sgupta.composite.home.components.TopHeadLineSection
import com.sgupta.composite.home.states.HomeScreenViewState
import com.sgupta.core.ViewEvent
import com.sgupta.core.components.SearchBar
import com.sgupta.core.theme.LightColors
import com.sgupta.core.theme.NewsAppTheme
import com.sgupta.core.theme.Typography

@Composable
fun NewsHomeScreen(state: HomeScreenViewState, onEvent: (ViewEvent) -> Unit) {
//    HomeScreenViewState.Loading
//    HomeScreenViewState.ApiSuccess()
    NewsAppTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightColors.surface),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_hamburger),
                            contentDescription = "Menu Icon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )

                        Text(
                            text = "Top News",
                            style = Typography.displayLarge.copy(fontSize = 18.sp),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                SearchBar(modifier = Modifier.padding(horizontal = 16.dp))
            }
            item {
                TopHeadLineSection()
            }
            items(3) {
                NewsListItem()
            }
            item {
                Text(
                    text = "Categories",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp),
                    textAlign = TextAlign.Start,
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(2) {
                CategoriesSectionItem()
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Countries News",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp),
                    textAlign = TextAlign.Start,
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(2) {
                CountriesSectionItem()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}