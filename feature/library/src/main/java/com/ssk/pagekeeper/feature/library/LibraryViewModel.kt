package com.ssk.pagekeeper.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssk.pagekeeper.core.domain.repository.BookRepository
import com.ssk.pagekeeper.core.domain.repository.ImportResult
import com.ssk.pagekeeper.feature.library.handler.LibraryAction
import com.ssk.pagekeeper.feature.library.handler.LibraryEvent
import com.ssk.pagekeeper.feature.library.handler.LibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val bookRepository: BookRepository,
) : ViewModel() {

    // Local UI state that the repository doesn't own (importing flag, dialog).
    private val transientState = MutableStateFlow(LibraryState())

    // Merge the repository's book list into the transient state on every emission.
    val state: StateFlow<LibraryState> = combine(
        transientState,
        bookRepository.books,
    ) { transient, books ->
        transient.copy(books = books)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STATE_TIMEOUT_MS), LibraryState())

    private val eventChannel = Channel<LibraryEvent>(Channel.BUFFERED)
    val events: Flow<LibraryEvent> = eventChannel.receiveAsFlow()

    fun onAction(action: LibraryAction) {
        when (action) {
            is LibraryAction.FilePicked -> importBook(action.uri)
            LibraryAction.DismissErrorDialog -> dismissErrorDialog()
            LibraryAction.OnNavDrawerClick -> eventChannel.trySend(LibraryEvent.OpenNavDrawer)
            LibraryAction.OnSearchClick -> eventChannel.trySend(LibraryEvent.SearchResults)
            is LibraryAction.OnDeleteClick -> deleteBook(action.bookId)
            is LibraryAction.OnFavoriteClick -> toggleFavorite(action.bookId)
            is LibraryAction.OnFinishClick -> TODO()
            is LibraryAction.OnShareClick -> TODO()
        }
    }

    private fun deleteBook(bookId: String) {
        viewModelScope.launch {
            bookRepository.deleteBook(bookId)
        }
    }

    private fun toggleFavorite(bookId: String) {
        val current = state.value.books.firstOrNull { it.id == bookId } ?: return
        viewModelScope.launch {
            bookRepository.setFavorite(bookId, !current.isFavorite)
        }
    }

    private fun importBook(uri: String) {
        viewModelScope.launch {
            transientState.update { it.copy(isImporting = true) }
            val result = bookRepository.importBook(uri)
            transientState.update { it.copy(isImporting = false) }
            when (result) {
                is ImportResult.Success -> Unit // books flow updates the state automatically
                ImportResult.Duplicate -> eventChannel.send(LibraryEvent.BookAlreadyImported)
                ImportResult.UnsupportedFormat -> transientState.update {
                    it.copy(errorDialog = LibraryState.ErrorDialog.UnsupportedFormat)
                }
                is ImportResult.Error -> eventChannel.send(LibraryEvent.ImportFailed(result.cause))
            }
        }
    }

    private fun dismissErrorDialog() {
        transientState.update { it.copy(errorDialog = null) }
    }

    private inline fun MutableStateFlow<LibraryState>.update(
        transform: (LibraryState) -> LibraryState,
    ) {
        value = transform(value)
    }

    private companion object {
        const val STATE_TIMEOUT_MS = 5_000L
    }
}
