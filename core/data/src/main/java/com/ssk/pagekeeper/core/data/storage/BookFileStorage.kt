package com.ssk.pagekeeper.core.data.storage

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Owns the on-disk layout for imported books:
 *
 *   filesDir/books/<id><ext>         ← copied source (extension preserved: .fb2/.epub/.txt/.pdf)
 *   filesDir/books/<id>.cover.jpg    ← extracted cover (if present)
 *
 * `<id>` is the SHA-256 hex of the source file bytes, which doubles as the book's primary key.
 */
@Singleton
class BookFileStorage @Inject constructor(
    @param:ApplicationContext private val context: android.content.Context,
) {
    private val booksDir: File by lazy {
        File(context.filesDir, "books").apply { mkdirs() }
    }

    private val contentResolver: ContentResolver get() = context.contentResolver

    /** Display name from the picker URI, or null if the provider didn't expose it. */
    fun queryDisplayName(uri: Uri): String? {
        val cursor = contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        return cursor?.use { if (it.moveToFirst()) it.getString(0) else null }
    }

    /** SHA-256 hex of the URI's contents. Reads the stream once. */
    fun sha256(uri: Uri): String {
        val digest = MessageDigest.getInstance("SHA-256")
        contentResolver.openInputStream(uri)?.use { stream ->
            val buffer = ByteArray(BUFFER_BYTES)
            while (true) {
                val read = stream.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
            }
        } ?: error("Could not open input stream for $uri")
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    /** Copy the picker URI into internal storage, keeping the original extension. Returns absolute path. */
    fun copyToInternalStorage(uri: Uri, id: String, extension: String): String {
        val target = File(booksDir, "$id$extension")
        contentResolver.openInputStream(uri)?.use { input ->
            target.outputStream().use { output -> input.copyTo(output) }
        } ?: error("Could not open input stream for $uri")
        return target.absolutePath
    }

    /** Write cover bytes for [id] to disk. Returns absolute path. */
    fun writeCover(id: String, bytes: ByteArray): String {
        val target = File(booksDir, "$id.cover.jpg")
        target.writeBytes(bytes)
        return target.absolutePath
    }

    private companion object {
        const val BUFFER_BYTES = 8 * 1024
    }
}
