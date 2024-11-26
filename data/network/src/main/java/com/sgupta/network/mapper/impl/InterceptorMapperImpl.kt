package com.sgupta.network.mapper.impl

import com.sgupta.network.client.NetworkHost
import com.sgupta.network.interceptor.ApiHeaderInterceptor
import com.sgupta.network.mapper.InterceptorMapper
import com.sgupta.network.mapper.NetworkHeaderManager
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterceptorMapperImpl @Inject constructor(
    private val networkHeaderManager: NetworkHeaderManager
) : InterceptorMapper {
    override fun convert(from: NetworkHost): Interceptor {
        val headers = networkHeaderManager.convert(from)
        return when (from) {
            NetworkHost.SERVER_BASE -> {
                ApiHeaderInterceptor(headers)
            }
        }
    }
}