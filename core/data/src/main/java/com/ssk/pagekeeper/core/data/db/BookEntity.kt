package com.ssk.pagekeeper.core.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ssk.pagekeeper.core.domain.model.Book
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(
    tableName = "books",
    indices = [Index("dateAddedEpochMillis")],
)
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val coverPath: String?,
    val filePath: String,
    val dateAddedEpochMillis: Long,
)

@OptIn(ExperimentalTime::class)
fun BookEntity.toDomain(): Book = Book(
    id = id,
    title = title,
    author = author,
    coverPath = coverPath,
    filePath = filePath,
    dateAdded = Instant.fromEpochMilliseconds(dateAddedEpochMillis),
)

@OptIn(ExperimentalTime::class)
fun Book.toEntity(): BookEntity = BookEntity(
    id = id,
    title = title,
    author = author,
    coverPath = coverPath,
    filePath = filePath,
    dateAddedEpochMillis = dateAdded.toEpochMilliseconds(),
)
