package com.sgupta.network.mapper.impl

import com.sgupta.network.client.NetworkHost
import com.sgupta.network.mapper.HostConfigurationMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostConfigurationMapperImpl @Inject constructor(): HostConfigurationMapper {
    override fun convert(from: NetworkHost): String {
        return when(from) {
            NetworkHost.SERVER_BASE -> "https://newsapi.org/v2/"
            NetworkHost.SERVER_GEMINI -> "https://generativelanguage.googleapis.com/v1beta/models"
        }
    }
}