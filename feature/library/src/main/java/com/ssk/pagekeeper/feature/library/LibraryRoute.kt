package com.ssk.pagekeeper.feature.library

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssk.pagekeeper.core.designsystem.ui.ObserveAsEvents
import com.ssk.pagekeeper.feature.library.handler.LibraryAction
import com.ssk.pagekeeper.feature.library.handler.LibraryEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun LibraryRoute(
    isMenuOpen: Boolean,
    onNavDrawerClick: () -> Unit,
    onSearchClick: () -> Unit,
    onImportClick: () -> Unit,
    pickedUriEvents: Flow<String>,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(pickedUriEvents) { uri ->
        viewModel.onAction(LibraryAction.FilePicked(uri))
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LibraryEvent.BookAlreadyImported ->
                snackbarHostState.showSnackbar("This book is already in your library.")
            is LibraryEvent.ImportFailed ->
                snackbarHostState.showSnackbar(
                    "Could not import the book: ${event.cause.message ?: "Unknown error"}",
                )

            LibraryEvent.OpenNavDrawer -> {
                onNavDrawerClick()
            }
            LibraryEvent.SearchResults -> {
                onSearchClick()
            }
        }
    }

    LibraryScreen(
        state = state,
        isMenuOpen = isMenuOpen,
        snackbarHostState = snackbarHostState,
        onImportClick = onImportClick,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}
