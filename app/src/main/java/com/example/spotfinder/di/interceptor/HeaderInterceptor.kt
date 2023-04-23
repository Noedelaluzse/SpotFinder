package com.example.spotfinder.di.interceptor

import com.example.spotfinder.util.Constants.Companion.X_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-token", X_TOKEN)
            .build()
        return chain.proceed(request)
    }

}