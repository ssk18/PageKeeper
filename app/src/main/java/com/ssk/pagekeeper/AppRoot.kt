package com.ssk.pagekeeper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssk.pagekeeper.presentation.splash.SplashScreen

/**
 * Top-level gate. Renders the splash until saved book metadata finishes restoring,
 * then swaps to [MainNavigation].
 */
@Composable
fun AppRoot(viewModel: AppViewModel = hiltViewModel()) {
    val isReady by viewModel.isReady.collectAsStateWithLifecycle()
    if (isReady) {
        MainNavigation()
    } else {
        SplashScreen()
    }
}
