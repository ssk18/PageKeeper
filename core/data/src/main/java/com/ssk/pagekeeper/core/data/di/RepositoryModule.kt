package com.ssk.pagekeeper.core.data.di

import com.ssk.pagekeeper.core.data.repository.BookRepositoryImpl
import com.ssk.pagekeeper.core.domain.repository.BookRepository
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
    abstract fun bindBookRepository(impl: BookRepositoryImpl): BookRepository
}
