package com.alessandrofarandagancio.anywhere.di

import com.alessandrofarandagancio.anywhere.BuildConfig
import com.alessandrofarandagancio.anywhere.api.duckduckgo.ApiHelper
import com.alessandrofarandagancio.anywhere.api.duckduckgo.ApiHelperImpl
import com.alessandrofarandagancio.anywhere.api.duckduckgo.ApiService
import com.alessandrofarandagancio.anywhere.api.duckduckgo.baseDuckDuckGoRestApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideYelpBaseUrl() = baseDuckDuckGoRestApiUrl

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideDuckDuckGo(
        okHttpClient: OkHttpClient,
        duckDuckGoBaseUrl: String
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(duckDuckGoBaseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}