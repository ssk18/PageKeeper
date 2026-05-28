package com.ssk.pagekeeper.core.data.parser

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resolves a [BookMetadataParser] by file extension. Hilt multibinding wires all registered
 * parsers into the `Set<BookMetadataParser>` constructor parameter, so adding a new format
 * = drop in a parser class and a `@Binds @IntoSet` line — no changes here.
 */
@Singleton
class BookMetadataParserRegistry @Inject constructor(
    parsers: Set<@JvmSuppressWildcards BookMetadataParser>,
) {
    private val byExtension: Map<String, BookMetadataParser> =
        parsers.flatMap { parser -> parser.supportedExtensions.map { ext -> ext.lowercase() to parser } }
            .toMap()

    val supportedExtensions: Set<String> = byExtension.keys

    fun parserFor(extension: String): BookMetadataParser? = byExtension[extension.lowercase()]
}
