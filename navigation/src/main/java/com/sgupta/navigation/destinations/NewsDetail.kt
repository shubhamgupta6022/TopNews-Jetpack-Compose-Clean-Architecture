package com.sgupta.navigation.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * The news detail screen destination
 */
data class NewsDetail(
    val title: String? = null,
    val url: String? = null
) : NewsDestination {
    override val route: String = "news_detail?title={title}&url={url}"

    override fun createRoute(): String = "news_detail" + buildQueryParams()

    private fun buildQueryParams(): String {
        val params = mutableListOf<String>()

        title?.let { params.add("title=$it") }
        url?.let { params.add("url=$it") }

        return if (params.isEmpty()) "" else "?" + params.joinToString("&")
    }

    override fun arguments(): List<NamedNavArgument> = listOf(
        navArgument("title") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
        navArgument("url") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    )

    companion object {
        fun fromNavBackStackEntry(entry: NavBackStackEntry): NewsDetail {
            val title = entry.arguments?.getString("title")
            val url = entry.arguments?.getString("url")
            return NewsDetail(title, url)
        }
    }
} 