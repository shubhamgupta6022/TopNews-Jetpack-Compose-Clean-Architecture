package com.sgupta.composite.home.model

data class CategoriesUiModel(
    val categoryType: CategoryType,
    val title: String,
    val icon: Int
)

enum class CategoryType {
    Sports,
    Business
}