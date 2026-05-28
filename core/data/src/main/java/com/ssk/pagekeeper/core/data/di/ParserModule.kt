package com.ssk.pagekeeper.core.data.di

import com.ssk.pagekeeper.core.data.parser.BookMetadataParser
import com.ssk.pagekeeper.core.data.parser.EpubMetadataParser
import com.ssk.pagekeeper.core.data.parser.Fb2MetadataParser
import com.ssk.pagekeeper.core.data.parser.PdfMetadataParser
import com.ssk.pagekeeper.core.data.parser.TxtMetadataParser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * Hilt multibinding registers each parser into the `Set<BookMetadataParser>` that
 * `BookMetadataParserRegistry` consumes. Adding a new format = drop a parser class and a
 * `@Binds @IntoSet` line.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ParserModule {
    @Binds @IntoSet
    abstract fun bindFb2Parser(impl: Fb2MetadataParser): BookMetadataParser

    @Binds @IntoSet
    abstract fun bindEpubParser(impl: EpubMetadataParser): BookMetadataParser

    @Binds @IntoSet
    abstract fun bindTxtParser(impl: TxtMetadataParser): BookMetadataParser

    @Binds @IntoSet
    abstract fun bindPdfParser(impl: PdfMetadataParser): BookMetadataParser
}
