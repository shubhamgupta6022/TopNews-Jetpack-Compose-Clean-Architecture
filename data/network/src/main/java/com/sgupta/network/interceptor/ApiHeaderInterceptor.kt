package com.sgupta.network.interceptor

import com.sgupta.network.header.HeaderMap
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton

/**
 *  Create interceptor with dynamic headers.
 *  @param [HeaderMap] create interceptor dynamically.
 */
@Singleton
class ApiHeaderInterceptor(
    private val header: HeaderMap = HeaderMap(),
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder =
            original.newBuilder().apply {
                header.addHeaders(original.headers())
                headers(header.getHeaders())
            }
        return chain.proceed(builder.build())
    }
}