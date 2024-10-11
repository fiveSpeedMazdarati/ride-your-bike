package com.example.rideyourbike.di

import com.example.rideyourbike.common.Constants
import com.example.rideyourbike.data.remote.StravaService
import com.example.rideyourbike.data.repository.StravaRepositoryImpl
import com.example.rideyourbike.domain.repository.StravaRepository
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
    fun provideActivitiesService(): StravaService {
        return Retrofit.Builder()
            .baseUrl(Constants.STRAVA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StravaService::class.java)
    }

    @Provides
    fun provideActivitiesRepository(api: StravaService): StravaRepository {
        return StravaRepositoryImpl(api)
    }
}