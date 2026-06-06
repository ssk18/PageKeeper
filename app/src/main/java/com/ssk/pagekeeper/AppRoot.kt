package com.ssk.pagekeeper

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.ssk.pagekeeper.feature.library.components.DrawerDestination
import com.ssk.pagekeeper.feature.library.components.PageKeeperDrawerSheet
import com.ssk.pagekeeper.feature.library.Library
import com.ssk.pagekeeper.feature.library.LibraryRoute
import com.ssk.pagekeeper.feature.library.rememberBookPicker
import com.ssk.pagekeeper.presentation.splash.SplashScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Top-level gate. Renders the splash until the BookRepository has emitted at least once,
 * then hands off to the Nav3 graph (start destination: Library) wrapped in the
 * app-wide [ModalNavigationDrawer].
 */
@Composable
fun AppRoot(
    viewModel: AppViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val isReady by viewModel.isReady.collectAsStateWithLifecycle()
    if (isReady) {
        val backStack = rememberNavBackStack(Library)
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedDestination by remember { mutableStateOf(DrawerDestination.Library) }

        // One-shot delivery: Channel emits each picked URI exactly once to the active
        // route's ObserveAsEvents collector. No replay on recomposition or rotation.
        val pickedUriChannel = remember { Channel<String>(Channel.BUFFERED) }
        val pickedUriEvents = remember(pickedUriChannel) { pickedUriChannel.receiveAsFlow() }

        val pickBook = rememberBookPicker { uri ->
            scope.launch { pickedUriChannel.send(uri) }
        }

        // `targetValue` flips the moment `open()`/`close()` is called — before the slide
        // animation completes. Using it (vs `isOpen`, which lags) lets the menu icon's
        // rotation/crossfade kick off in sync with the drawer's slide-in.
        val isMenuOpen = drawerState.targetValue == DrawerValue.Open

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                PageKeeperDrawerSheet(
                    selected = selectedDestination,
                    onSelected = { destination ->
                        selectedDestination = destination
                        scope.launch { drawerState.close() }
                    },
                    isOpen = isMenuOpen,
                    onDrawerClose = { scope.launch { drawerState.close() } },
                    onImportClick = {
                        scope.launch { drawerState.close() }
                        pickBook()
                    },
                )
            },
        ) {
            NavDisplay(
                backStack = backStack,
                modifier = modifier,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Library> {
                        LibraryRoute(
                            isMenuOpen = isMenuOpen,
                            onNavDrawerClick = { scope.launch { drawerState.open() } },
                            onSearchClick = { /* TODO: search route lands in a later milestone */ },
                            onImportClick = pickBook,
                            pickedUriEvents = pickedUriEvents,
                        )
                    }
                },
            )
        }
    } else {
        SplashScreen(modifier = modifier)
    }
}
