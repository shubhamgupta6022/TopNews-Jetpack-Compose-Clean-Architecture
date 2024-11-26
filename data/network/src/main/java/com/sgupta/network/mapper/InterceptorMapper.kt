package com.sgupta.network.mapper

import com.sgupta.core.Mapper
import com.sgupta.network.client.NetworkHost
import okhttp3.Interceptor

interface InterceptorMapper : Mapper<NetworkHost, Interceptor>