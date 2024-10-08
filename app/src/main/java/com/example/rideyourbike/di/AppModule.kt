package com.example.rideyourbike.di

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.ActivitiesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideActivitiesService(): ActivitiesService {
        return Retrofit.Builder()
            .baseUrl(Constants.ACTIVITIES_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivitiesService::class.java)
    }
}