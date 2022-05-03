package com.example.employeedirectory.core.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.employeedirectory.data.network.EmployeesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File

const val BASE_URL = "https://s3.amazonaws.com/sq-mobile-interview/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache? {
        val cacheSize = 10 * 1024 * 1024L // 10MB
        var cache: Cache? = null
        // Install an HTTP cache in the application cache directory.
        try {
            val cacheDir = File(context.cacheDir, "http")
            cache = Cache(cacheDir, cacheSize)
        } catch (e: Exception) {
            Timber.e(e, "Unable to install disk cache.")
        }

        return cache
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(cache: Cache?, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.cache(cache)

        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideEmployeesService(retrofit: Retrofit): EmployeesService {
        return retrofit.create(EmployeesService::class.java)
    }
}