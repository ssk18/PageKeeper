package com.ssk.pagekeeper.core.data.di

import android.content.Context
import androidx.room.Room
import com.ssk.pagekeeper.core.data.db.BookDao
import com.ssk.pagekeeper.core.data.db.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context): BookDatabase = Room.databaseBuilder(
        context = context,
        klass = BookDatabase::class.java,
        name = DATABASE_NAME,
    ).build()

    @Provides
    fun provideBookDao(database: BookDatabase): BookDao = database.bookDao()

    private const val DATABASE_NAME = "pagekeeper.db"
}
