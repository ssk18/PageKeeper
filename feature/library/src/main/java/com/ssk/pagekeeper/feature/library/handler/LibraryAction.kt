package com.ssk.pagekeeper.feature.library.handler

sealed interface LibraryAction {
    /** User picked a file from the system picker. */
    data class FilePicked(val uri: String) : LibraryAction

    /** User tapped OK on the unsupported-format dialog. */
    data object DismissErrorDialog : LibraryAction
    data object OnSearchClick: LibraryAction
    data object OnNavDrawerClick: LibraryAction
}
