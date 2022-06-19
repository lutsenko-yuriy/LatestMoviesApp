package com.example.latestmoviesapp.di.repos

import com.example.latestmoviesapp.data.movies.repos.LatestMoviesRepo
import com.example.latestmoviesapp.data.movies.repos.LatestMoviesRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MoviesRepoModule {

    @Binds
    @Singleton
    fun bindLatestMoviesRepo(repo: LatestMoviesRepoImpl): LatestMoviesRepo

}