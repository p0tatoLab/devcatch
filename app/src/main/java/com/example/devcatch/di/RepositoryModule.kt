package com.example.devcatch.di

import com.example.devcatch.data.repository.NewsSourceRepositoryImpl
import com.example.devcatch.domain.repository.NewsSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsSourceRepository(
        impl: NewsSourceRepositoryImpl
    ): NewsSourceRepository
}