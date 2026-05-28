package com.ssk.pagekeeper.core.domain.repository

import com.ssk.pagekeeper.core.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    /** Imported books, newest first. Emits whenever the library changes. */
    val books: Flow<List<Book>>

    /**
     * Import a book from a content URI (as a string, to keep this interface free of
     * Android-specific types). Returns one of the [ImportResult] variants.
     */
    suspend fun importBook(sourceUri: String): ImportResult
}

sealed interface ImportResult {
    data class Success(val book: Book) : ImportResult
    data object Duplicate : ImportResult
    data object UnsupportedFormat : ImportResult
    data class Error(val cause: Throwable) : ImportResult
}
