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
abstract class DataModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): CalculatorDatabase = CalculatorDatabase.initialize(context)

        @Singleton
        @Provides
        fun provideHistoryRepository(
            database: CalculatorDatabase
        ): HistoryRepository = HistoryRepositoryImpl(database)

        @Singleton
        @Provides
        fun provideCalculatorRepository(
        ): CalculatorRepository = CalculatorRepositoryImpl()
    }
}