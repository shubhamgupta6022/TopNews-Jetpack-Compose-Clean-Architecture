package com.sgupta.domain

interface TopNewsRepo {
    suspend fun getTopNews()
}