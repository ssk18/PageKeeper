package com.ssk.pagekeeper.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 2, exportSchema = true)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
