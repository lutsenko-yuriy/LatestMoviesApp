package com.example.latestmoviesapp.di.network

import android.content.Context
import android.os.Build
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkArgumentsModule {

    @Provides
    @Singleton
    fun provideCompileTimeArguments(@ApplicationContext context: Context): NetworkCompileTimeArguments {
        return NetworkCompileTimeArguments(
            baseUrl = context.getString(R.string.base_url),
            apiKey = context.getString(R.string.api_key),
        )
    }

    @Provides
    @Singleton
    fun provideRuntimeArguments(@ApplicationContext context: Context): NetworkRuntimeArguments {
        @Suppress("DEPRECATION")
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return NetworkRuntimeArguments(
            latestReleaseDate = Calendar.getInstance(),
            locale = locale,
        )
    }

}