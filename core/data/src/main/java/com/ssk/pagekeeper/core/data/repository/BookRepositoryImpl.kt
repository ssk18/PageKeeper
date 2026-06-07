package com.ssk.pagekeeper.core.data.repository

import android.net.Uri
import com.ssk.pagekeeper.core.data.db.BookDao
import com.ssk.pagekeeper.core.data.db.toDomain
import com.ssk.pagekeeper.core.data.db.toEntity
import com.ssk.pagekeeper.core.data.parser.BookMetadataParserRegistry
import com.ssk.pagekeeper.core.data.storage.BookFileStorage
import com.ssk.pagekeeper.core.domain.model.Book
import com.ssk.pagekeeper.core.domain.repository.BookRepository
import com.ssk.pagekeeper.core.domain.repository.ImportResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val dao: BookDao,
    private val storage: BookFileStorage,
    private val parserRegistry: BookMetadataParserRegistry,
) : BookRepository {

    override val books: Flow<List<Book>> = dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    @OptIn(ExperimentalTime::class)
    override suspend fun importBook(sourceUri: String): ImportResult = withContext(Dispatchers.IO) {
        runCatching {
            val uri = Uri.parse(sourceUri)
            val displayName = storage.queryDisplayName(uri)
                ?: return@runCatching ImportResult.Error(IllegalStateException("Could not read file name"))

            val extension = "." + displayName.substringAfterLast('.', "").lowercase()
            val parser = parserRegistry.parserFor(extension)
                ?: return@runCatching ImportResult.UnsupportedFormat

            val id = storage.sha256(uri)
            if (dao.exists(id)) {
                return@runCatching ImportResult.Duplicate
            }

            val filePath = storage.copyToInternalStorage(uri, id, extension)
            val metadata = parser.parse(File(filePath), displayName)
            val coverPath = metadata.coverBytes?.let { bytes -> storage.writeCover(id, bytes) }

            val book = Book(
                id = id,
                title = metadata.title.orFallback { displayName.substringBeforeLast('.', displayName) },
                author = metadata.author.orFallback { "Unknown author" },
                coverPath = coverPath,
                filePath = filePath,
                dateAdded = Clock.System.now(),
            )
            dao.insert(book.toEntity())
            ImportResult.Success(book)
        }.getOrElse { ImportResult.Error(it) }
    }

    override suspend fun deleteBook(bookId: String) {
        withContext(Dispatchers.IO) {
            dao.deleteBook(bookId)
        }
    }

    override suspend fun setFavorite(bookId: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            dao.setFavorite(bookId, isFavorite)
        }
    }

    private inline fun String?.orFallback(fallback: () -> String): String = this?.takeIf { it.isNotBlank() } ?: fallback()
}
