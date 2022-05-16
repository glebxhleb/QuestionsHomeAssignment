package com.shuster.testapp.di

import com.shuster.testapp.data.repository.QuestionsRepositoryImpl
import com.shuster.testapp.domain.repository.QuestionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun mapQuestionsRepository(repository: QuestionsRepositoryImpl): QuestionsRepository
}