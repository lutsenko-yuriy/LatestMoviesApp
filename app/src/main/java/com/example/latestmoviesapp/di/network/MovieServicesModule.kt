package com.example.latestmoviesapp.di.network

import com.example.latestmoviesapp.data.movies.services.DiscoverLatestMoviesNetworkService
import com.example.latestmoviesapp.data.movies.services.MovieDetailsNetworkService
import com.example.latestmoviesapp.data.movies.services.MoviesByQueryNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieServicesModule {

    @Provides
    @Singleton
    fun provideDiscoverLatestMoviesNetworkService(retrofit: Retrofit): DiscoverLatestMoviesNetworkService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideMoviesByQueryNetworkService(retrofit: Retrofit): MoviesByQueryNetworkService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideMovieDetailsNetworkService(retrofit: Retrofit): MovieDetailsNetworkService {
        return retrofit.create()
    }

}