package com.shuster.testapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shuster.testapp.BuildConfig
import com.shuster.testapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    @Provides
//    @Singleton
//    fun provideCache(@ApplicationContext context: Context): Cache {
//        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
//        val httpCacheDirectory = File(context.cacheDir, "http-cache")
//        return Cache(httpCacheDirectory, cacheSize)
//    }

    @Provides
    @Singleton
    internal fun provideOkHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
//        cache: Cache,
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
//        httpClient.cache(cache)
        httpClient.addInterceptor(logger)
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    internal fun provideApiService(gsonBuilder: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BaseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}