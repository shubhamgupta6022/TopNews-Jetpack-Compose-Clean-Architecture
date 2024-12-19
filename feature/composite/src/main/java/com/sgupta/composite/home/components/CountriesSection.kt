package com.sgupta.composite.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sgupta.core.theme.DarkGray
import com.sgupta.core.theme.LightGray
import com.sgupta.core.theme.NeutralBlack
import com.sgupta.core.theme.Typography

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CountriesSection() {
    LazyColumn {
        items(3) {
            CountriesSectionItem()
        }
    }
}

@Composable
fun CountriesSectionItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        Column {
            CountriesItem(
                title = "Sports"
            )
            Divider(
                modifier = Modifier.padding(top = 4.dp),
                color = LightGray,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun CountriesItem(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
                    contentScale = ContentScale.Crop
                ),
                contentDescription = title,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = Typography.headlineMedium.copy(color = DarkGray, fontSize = 16.sp)
            )
        }
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGray
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.height(32.dp)
        ) {
            Text(
                text = "Filter",
                style = Typography.headlineMedium.copy(color = NeutralBlack, fontSize = 16.sp)
            )
        }
    }
}
