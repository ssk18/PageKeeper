package com.ssk.pagekeeper.feature.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssk.pagekeeper.core.designsystem.theme.PageKeeperTheme
import com.ssk.pagekeeper.core.domain.model.Book
import com.ssk.pagekeeper.feature.library.components.ImportBookButton
import com.ssk.pagekeeper.feature.library.components.PageKeeperTopBar
import com.ssk.pagekeeper.feature.library.handler.LibraryAction
import com.ssk.pagekeeper.feature.library.handler.LibraryState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    state: LibraryState,
    isMenuOpen: Boolean,
    snackbarHostState: SnackbarHostState,
    onImportClick: () -> Unit,
    onAction: (LibraryAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            PageKeeperTopBar(
                isMenuOpen = isMenuOpen,
                onSearchClick = { onAction(LibraryAction.OnSearchClick) },
                onNavDrawerClick = { onAction(LibraryAction.OnNavDrawerClick) },
                modifier = Modifier.statusBarsPadding(),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.books.isEmpty()) {
                LibraryEmptyState(
                    onImportClick = onImportClick,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                LibraryContent(
                    modifier = Modifier.fillMaxSize(),
                    books = state.books,
                    onImportClick = onImportClick,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onFavoriteClick = { id ->
                        onAction(LibraryAction.OnFavoriteClick(id))
                    },
                    onShareClick = { id ->
                        onAction(LibraryAction.OnShareClick(id))
                    },
                    onFinishedClick = {id ->
                        onAction(LibraryAction.OnFinishClick(id))
                    },
                    onDeleteClick = { id ->
                        onAction(LibraryAction.OnDeleteClick(id))
                    }
                )
            }

            if (state.isImporting) {
                LoadingOverlay(modifier = Modifier.fillMaxSize())
            }
        }
    }

    if (state.errorDialog == LibraryState.ErrorDialog.UnsupportedFormat) {
        UnsupportedFormatDialog(onDismiss = { onAction(LibraryAction.DismissErrorDialog) })
    }
}

@Composable
private fun LibraryEmptyState(
    onImportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(com.ssk.pagekeeper.core.designsystem.R.drawable.page_keeper),
            contentDescription = "Book icon",
            modifier = Modifier
                .size(73.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Your library is empty.",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Import your first book to start\n" +
                    "building your library",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        ImportBookButton(onClick = onImportClick)
    }
}

@Composable
private fun LibraryContent(
    modifier: Modifier = Modifier,
    books: List<Book>,
    onImportClick: () -> Unit,
    contentPadding: PaddingValues,
    onFavoriteClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onFinishedClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(books, key = { it.id }) { book ->
            BookCard(
                book = book,
                isFinished = false,
                onFavoriteClick = onFavoriteClick,
                onShareClick = onShareClick,
                onFinishedClick = onFinishedClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun LoadingOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.4f))
            .pointerInput(Unit) { awaitEachGesture { /* swallow all gestures */ } },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun UnsupportedFormatDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Unsupported format") },
        text = { Text("Supported formats: FB2, EPUB, TXT, and PDF.") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        },
    )
}

// ---------- Previews ----------

@Preview(showBackground = true, name = "Empty library")
@Composable
private fun LibraryScreenEmptyPreview() {
    PageKeeperTheme {
        LibraryScreen(
            state = LibraryState(),
            isMenuOpen = false,
            snackbarHostState = remember { SnackbarHostState() },
            onImportClick = {},
            onAction = {},
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true, name = "Populated library")
@Composable
private fun LibraryScreenPopulatedPreview() {
    val sampleBooks = listOf(
        Book(
            id = "1",
            title = "The Adventures of Tom Sawyer",
            author = "Mark Twain",
            coverPath = null,
            filePath = "/tmp/1.fb2",
            dateAdded = Clock.System.now(),
        ),
        Book(
            id = "2",
            title = "Frankenstein",
            author = "Mary Shelley",
            coverPath = null,
            filePath = "/tmp/2.fb2",
            dateAdded = Clock.System.now(),
        ),
    )
    PageKeeperTheme {
        LibraryScreen(
            state = LibraryState(books = sampleBooks),
            isMenuOpen = false,
            snackbarHostState = remember { SnackbarHostState() },
            onImportClick = {},
            onAction = {},
        )
    }
}

@Preview(showBackground = true, name = "Importing overlay")
@Composable
private fun LibraryScreenImportingPreview() {
    PageKeeperTheme {
        LibraryScreen(
            state = LibraryState(isImporting = true),
            isMenuOpen = false,
            snackbarHostState = remember { SnackbarHostState() },
            onImportClick = {},
            onAction = {},
        )
    }
}

@Preview(showBackground = true, name = "Unsupported format dialog")
@Composable
private fun LibraryScreenUnsupportedFormatPreview() {
    PageKeeperTheme {
        LibraryScreen(
            state = LibraryState(errorDialog = LibraryState.ErrorDialog.UnsupportedFormat),
            isMenuOpen = false,
            snackbarHostState = remember { SnackbarHostState() },
            onImportClick = {},
            onAction = {},
        )
    }
}
