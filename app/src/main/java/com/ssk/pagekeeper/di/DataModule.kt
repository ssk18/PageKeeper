package com.ssk.pagekeeper.di

import com.ssk.pagekeeper.core.data.repository.DataRepositoryImpl
import com.ssk.pagekeeper.core.domain.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindDataRepository(impl: DataRepositoryImpl): DataRepository
}
