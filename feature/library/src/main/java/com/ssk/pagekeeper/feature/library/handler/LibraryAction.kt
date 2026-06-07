package com.ssk.pagekeeper.feature.library.handler

sealed interface LibraryAction {
    data class FilePicked(val uri: String) : LibraryAction
    data object DismissErrorDialog : LibraryAction
    data object OnSearchClick: LibraryAction
    data object OnNavDrawerClick: LibraryAction
    data class OnFavoriteClick(val bookId: String): LibraryAction
    data class OnShareClick(val bookId: String): LibraryAction
    data class OnFinishClick(val bookId: String): LibraryAction
    data class OnDeleteClick(val bookId: String): LibraryAction
}