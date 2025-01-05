package com.sgupta.composite.home.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgupta.composite.R
import com.sgupta.composite.home.model.CategoriesUiModel
import com.sgupta.composite.home.model.CategoryType
import com.sgupta.core.theme.Typography
import com.sgupta.core.theme.colorGrey100
import com.sgupta.core.theme.colorGrey700
import com.sgupta.core.theme.colorGreyLight

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoriesSection() {
    LazyColumn {
        items(3) {
            CategoriesSectionItem(
                CategoriesUiModel(
                    categoryType = CategoryType.Sports,
                    title = "Sports",
                    icon = R.drawable.ic_sports
                )
            )
        }
    }
}

@Composable
fun CategoriesSectionItem(category: CategoriesUiModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        Column {
            CategoryItem(category)
            Divider(
                modifier = Modifier.padding(top = 4.dp),
                color = colorGreyLight,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoriesUiModel
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
            Icon(
                painter = painterResource(id = category.icon),
                contentDescription = category.title,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category.title,
                style = Typography.headlineMedium.copy(color = colorGrey700, fontSize = 16.sp)
            )
        }
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorGrey100
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.height(32.dp)
        ) {
            Text(
                text = "View All",
                style = Typography.headlineMedium.copy(color = colorGrey700, fontSize = 16.sp)
            )
        }
    }
}
