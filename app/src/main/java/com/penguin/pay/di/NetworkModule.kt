package com.penguin.pay.di

import com.penguin.pay.network.NetworkDataSource
import com.penguin.pay.network.retrofit.RetrofitNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY) // should change in production
                },
        )
        .build()


    @Provides
    @Singleton
    fun provideNetworkDataSource(
        networkJson: Json,
        okhttpCallFactory: Call.Factory
    ): NetworkDataSource = RetrofitNetwork(networkJson, okhttpCallFactory)
}