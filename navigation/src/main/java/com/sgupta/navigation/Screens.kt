package com.sgupta.navigation

enum class Screens(val id: String) {
    Home("home"),
    Listing("listing");

    fun withArgs(vararg args: Pair<String, String?>): String {
        return buildString {
            append(id)
            if (args.isNotEmpty()) {
                append("?")
                args.filter { it.second != null }.forEachIndexed { index, entry ->
                    append("${entry.first}=${entry.second}")
                    if (index < args.size - 1) append("&")
                }
            }
        }
    }
}