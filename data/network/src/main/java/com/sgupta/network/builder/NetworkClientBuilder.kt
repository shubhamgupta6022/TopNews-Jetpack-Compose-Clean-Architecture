package com.sgupta.network.builder

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClientBuilder {
    private val retrofitBuilder = Retrofit.Builder()
    private val okHttpClientBuilder = OkHttpClient.Builder()

    fun build(baseUrl: String): Retrofit {
        if (baseUrl.isEmpty()) throw Exception("Base url expected")

        val okHttpClient: OkHttpClient = provideOkHttpClient()
        return retrofitBuilder
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * adds converter factory for serialization/deserialization
     *
     * @param converterFactory Provides the converterFactory to be used on response
     */
    fun addConverterFactory(converterFactory: Converter.Factory): NetworkClientBuilder {
        retrofitBuilder.addConverterFactory(converterFactory)
        return this
    }

    /**
     * adds call adapter factory
     *
     * @param callAdapterFactory Provides the callAdapterFactory to be used for response
     */
    fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): NetworkClientBuilder {
        retrofitBuilder.addCallAdapterFactory(callAdapterFactory)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): NetworkClientBuilder {
        okHttpClientBuilder.addInterceptor(interceptor)
        return this
    }

    /**
     * Creates okhttp client to be used by retrofit
     */
    private fun provideOkHttpClient(): OkHttpClient = okHttpClientBuilder.build()

}