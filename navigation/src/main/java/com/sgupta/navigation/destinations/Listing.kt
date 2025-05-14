package com.sgupta.navigation.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * The news listing screen destination
 */
data class Listing(
    val country: String? = null,
    val category: String? = null
) : NewsDestination {
    override val route: String = "listing?country={country}&category={category}"

    override fun createRoute(): String = "listing" + buildQueryParams()

    private fun buildQueryParams(): String {
        val params = mutableListOf<String>()

        country?.let { params.add("country=$it") }
        category?.let { params.add("category=$it") }

        return if (params.isEmpty()) "" else "?" + params.joinToString("&")
    }

    override fun arguments(): List<NamedNavArgument> = listOf(
        navArgument("country") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
        navArgument("category") {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    )

    companion object {
        fun fromNavBackStackEntry(entry: NavBackStackEntry): Listing {
            val country = entry.arguments?.getString("country")
            val category = entry.arguments?.getString("category")
            return Listing(country, category)
        }
    }
}