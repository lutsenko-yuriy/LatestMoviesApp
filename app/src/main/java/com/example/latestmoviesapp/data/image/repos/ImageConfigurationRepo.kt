package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.domain.images.ImageConfiguration

interface ImageConfigurationRepo {
    suspend fun getImageConfiguration(): ImageConfiguration
}