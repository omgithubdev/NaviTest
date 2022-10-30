package com.omagrahari.navitest.di

import com.omagrahari.navitest.BuildConfig
import com.omagrahari.navitest.data.network.ApiService
import com.omagrahari.navitest.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.networkInterceptors().add(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Authorization", "Bearer ${BuildConfig.AUTH_TOKEN}")
                return chain.proceed(requestBuilder.build())
            }
        })

        return okHttpClient.build()
    }

}