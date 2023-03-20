package com.example.calcnumeric.di

import com.example.calcnumeric.data.repository.HistoryRepositoryImpl
import com.example.calcnumeric.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Singleton
    @Provides
    fun provideHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository = impl
}