package com.example.calcnumeric.di

import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.repository.CalculatorRepository
import com.example.calcnumeric.domain.repository.HistoryRepository
import com.example.calcnumeric.domain.usecase.CalculateExpressionUseCase
import com.example.calcnumeric.domain.usecase.ClearHistoryUseCase
import com.example.calcnumeric.domain.usecase.DeleteHistoryByExpressionUseCase
import com.example.calcnumeric.domain.usecase.GetHistoryAllUseCase
import com.example.calcnumeric.domain.usecase.InsertHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideCalculateExpressionUseCase(
        repository: CalculatorRepository
    ) = CalculateExpressionUseCase(repository)

    @Provides
    fun provideClearHistoryUseCase(
        repository: HistoryRepository
    ) = ClearHistoryUseCase(repository)

    @Provides
    fun provideDeleteHistoryByExpressionUseCase(
        repository: HistoryRepository
    ) = DeleteHistoryByExpressionUseCase(repository)

    @Provides
    fun provideGetHistoryAllUseCase(
        repository: HistoryRepository,
        dispatcher: DispatcherProvider
    ) = GetHistoryAllUseCase(repository, dispatcher)

    @Provides
    fun provideInsertHistoryUseCase(
        repository: HistoryRepository
    ) = InsertHistoryUseCase(repository)
}