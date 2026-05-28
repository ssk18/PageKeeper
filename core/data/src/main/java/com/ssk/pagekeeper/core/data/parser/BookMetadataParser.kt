package com.ssk.pagekeeper.core.data.parser

import java.io.File

/**
 * Pluggable per-format parser. Implementations advertise the extensions they handle and
 * return whatever metadata they can extract — any field may be null when the format
 * doesn't carry it (e.g. TXT has no embedded title, PDF has no cover-bytes contract).
 *
 * The repository falls back to a sensible default when a field is missing.
 */
interface BookMetadataParser {
    /** Extensions handled by this parser, lowercase, dot-prefixed (e.g. ".fb2"). */
    val supportedExtensions: Set<String>

    /**
     * @param file the FB2/EPUB/TXT/PDF file already copied into internal storage
     * @param displayName the original file name from the picker (used as fallback for title)
     */
    fun parse(file: File, displayName: String): Metadata
}

data class Metadata(
    val title: String?,
    val author: String?,
    val coverBytes: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Metadata
        if (title != other.title) return false
        if (author != other.author) return false
        if (!coverBytes.contentEquals(other.coverBytes)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (author?.hashCode() ?: 0)
        result = 31 * result + (coverBytes?.contentHashCode() ?: 0)
        return result
    }
}
