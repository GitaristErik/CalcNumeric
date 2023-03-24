package com.example.calcnumeric.di

import android.content.Context
import com.example.calcnumeric.data.datasource.CalculatorDatabase
import com.example.calcnumeric.data.repository.CalculatorRepositoryImpl
import com.example.calcnumeric.data.repository.HistoryRepositoryImpl
import com.example.calcnumeric.domain.repository.CalculatorRepository
import com.example.calcnumeric.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Singleton
    @Provides
    fun provideHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository = impl

    @Singleton
    @Provides
    fun provideCalculatorRepository(impl: CalculatorRepositoryImpl): CalculatorRepository = impl

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CalculatorDatabase = CalculatorDatabase.initialize(context)
}