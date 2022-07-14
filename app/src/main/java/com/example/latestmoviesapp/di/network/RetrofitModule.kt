package com.example.latestmoviesapp.di.network

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(configuration: NetworkCompileTimeArguments): Retrofit {
        // TODO - read about passing API keys via OkHttpClient.Builder()...
        return Retrofit.Builder()
            .baseUrl(configuration.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}