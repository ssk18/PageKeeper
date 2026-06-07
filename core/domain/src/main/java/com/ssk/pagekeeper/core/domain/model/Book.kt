package com.ssk.pagekeeper.core.domain.model

import kotlin.time.Instant

/**
 * A book the user has imported into the library.
 *
 * @property id Stable identifier — SHA-256 hex of the source file bytes. Doubles as dedup key.
 * @property coverPath Absolute path to the extracted cover image in internal storage, or null
 *   if the FB2 had no cover.
 * @property filePath Absolute path to the FB2 file copied into internal storage. Used by future
 *   milestones for full parsing and reading.
 */
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverPath: String?,
    val filePath: String,
    val dateAdded: Instant,
    val isFavorite: Boolean = false,
)
