package com.ssk.pagekeeper.feature.library.handler

import androidx.compose.runtime.Immutable
import com.ssk.pagekeeper.core.domain.model.Book

@Immutable
data class LibraryState(
    val books: List<Book> = emptyList(),
    val isImporting: Boolean = false,
    val errorDialog: ErrorDialog? = null,
) {
    sealed interface ErrorDialog {
        /** Selected file did not have an .fb2 extension. */
        data object UnsupportedFormat : ErrorDialog
    }
}
