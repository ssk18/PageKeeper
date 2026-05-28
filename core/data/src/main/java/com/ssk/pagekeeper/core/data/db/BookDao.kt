package com.ssk.pagekeeper.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    /** Books newest-first; first emission is the initial snapshot (even if empty). */
    @Query("SELECT * FROM books ORDER BY dateAddedEpochMillis DESC")
    fun observeAll(): Flow<List<BookEntity>>

    /** Errors if [BookEntity.id] already exists — repository dedups beforehand anyway. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(book: BookEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM books WHERE id = :id)")
    suspend fun exists(id: String): Boolean
}
