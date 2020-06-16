package ru.valya1.skyengtest.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.valya1.skyengtest.BuildConfig
import ru.valya1.skyengtest.domain.data.network.MeaningsAPI
import javax.inject.Singleton

@Module
class ApiModule {
    
    @Provides
    @Singleton
    fun okhttpClient() = OkHttpClient.Builder().build()
    
    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.serverEndpoint)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .addConverterFactory(
            Json(JsonConfiguration(ignoreUnknownKeys = true, isLenient = true))
                .asConverterFactory(MediaType.parse("application/json")!!)
        )
        .build()
    
    @Provides
    @Singleton
    fun api(retrofit: Retrofit): MeaningsAPI = retrofit.create(MeaningsAPI::class.java)
    
}