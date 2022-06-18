package com.example.latestmoviesapp.di.network

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArgumentsConfiguration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(configuration: NetworkCompileTimeArgumentsConfiguration): Retrofit {
        return Retrofit.Builder()
            .baseUrl(configuration.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}