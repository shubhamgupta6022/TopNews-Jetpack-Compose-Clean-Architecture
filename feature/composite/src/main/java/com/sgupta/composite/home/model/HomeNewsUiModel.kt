package com.sgupta.composite.home.model

import com.sgupta.feature.composite.R
import com.sgupta.domain.model.ArticleDataModel

data class HomeNewsUiModel(
    val topNewsItemsList: List<ArticleDataModel> = emptyList(),
    val articlesItemsList: List<ArticleDataModel> = emptyList(),
    val countriesItemsList: List<CountriesUiModel> = getCountryList(),
    val categoriesItemsList: List<CategoriesUiModel> = getCategoryList()
)

private fun getCountryList() = listOf(
    CountriesUiModel("in", "India", R.drawable.ic_india),
    CountriesUiModel("uk", "UK", R.drawable.ic_uk),
    CountriesUiModel("us", "USA", R.drawable.ic_us)
)

private fun getCategoryList() = listOf(
    CategoriesUiModel(categoryType = CategoryType.Sports, title = "Sports", icon = R.drawable.ic_sports),
    CategoriesUiModel(categoryType = CategoryType.Business, title = "Business", icon = R.drawable.ic_business)
)