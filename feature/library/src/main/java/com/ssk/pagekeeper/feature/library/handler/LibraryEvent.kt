package com.ssk.pagekeeper.feature.library.handler

sealed interface LibraryEvent {
    /** Trying to import a file whose hash already exists in the library. */
    data object BookAlreadyImported : LibraryEvent

    /** Unexpected failure during import (I/O, parsing, etc.). */
    data class ImportFailed(val cause: Throwable) : LibraryEvent
    data object OpenNavDrawer: LibraryEvent
    data object SearchResults: LibraryEvent
}
