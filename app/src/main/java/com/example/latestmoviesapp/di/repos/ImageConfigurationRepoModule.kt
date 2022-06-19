package com.example.latestmoviesapp.di.repos

import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ImageConfigurationRepoModule {

    @Binds
    @Singleton
    fun bindImageConfigurationRepo(repo: ImageConfigurationRepoImpl): ImageConfigurationRepo

}