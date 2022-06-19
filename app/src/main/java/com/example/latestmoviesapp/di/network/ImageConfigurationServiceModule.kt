package com.example.latestmoviesapp.di.network

import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageConfigurationServiceModule {

    @Provides
    @Singleton
    fun provideImageConfigurationService(retrofit: Retrofit): ImageConfigurationService {
        return retrofit.create()
    }

}