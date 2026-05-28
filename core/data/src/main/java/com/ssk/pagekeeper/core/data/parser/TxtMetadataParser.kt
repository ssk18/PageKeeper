package com.ssk.pagekeeper.core.data.parser

import java.io.File
import javax.inject.Inject

/**
 * Plain text has no embedded metadata — every field returns null so the repository falls back
 * to the picker's display name for the title and "Unknown author" for the author.
 */
class TxtMetadataParser @Inject constructor() : BookMetadataParser {
    override val supportedExtensions: Set<String> = setOf(".txt")

    override fun parse(file: File, displayName: String): Metadata = Metadata(
        title = null,
        author = null,
        coverBytes = null,
    )
}
