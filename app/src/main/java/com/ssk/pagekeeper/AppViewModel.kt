package com.ssk.pagekeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssk.pagekeeper.core.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Tracks the one piece of state the splash gate cares about: have we restored
 * persisted book data? "Ready" = the BookRepository has emitted at least once
 * (which Room does as soon as the DB is open, even when the books table is empty).
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    bookRepository: BookRepository,
) : ViewModel() {
    val isReady: StateFlow<Boolean> = bookRepository.books
        .map { true }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
