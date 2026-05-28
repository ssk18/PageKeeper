package com.ssk.pagekeeper.feature.library

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

@Composable
fun LibraryRoute(
    isMenuOpen: Boolean,
    onNavDrawerClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri ->
        // Picker dismissed without a selection → uri is null → no-op (spec requirement).
        if (uri != null) {
            viewModel.onAction(LibraryAction.FilePicked(uri.toString()))
        }
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
        onImportClick = { filePickerLauncher.launch(arrayOf("*/*")) },
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}
