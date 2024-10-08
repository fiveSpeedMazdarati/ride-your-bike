package com.example.rideyourbike.di

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.ActivitiesService
import com.example.rideyourbike.data.repository.ActivitiesRepositoryImpl
import com.example.rideyourbike.domain.repository.ActivitiesRepository
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
            .baseUrl(Constants.STRAVA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActivitiesService::class.java)
    }

    @Provides
    fun provideActivitiesRepository(api: ActivitiesService): ActivitiesRepository {
        return ActivitiesRepositoryImpl(api)
    }
}