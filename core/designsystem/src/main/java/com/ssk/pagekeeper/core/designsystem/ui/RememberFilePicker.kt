package com.ssk.pagekeeper.core.designsystem.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

/**
 * Composable hook wrapping `ActivityResultContracts.OpenDocument`. Returns a
 * stable `launch` lambda whose identity does not change with [mimeTypes] or
 * [onResult] reference churn — both are captured via `rememberUpdatedState`.
 * Picker dismissals (null URI) are swallowed by contract.
 */
@Composable
fun rememberFilePicker(
    mimeTypes: Array<String>,
    onResult: (Uri) -> Unit,
): () -> Unit {
    val latestOnResult by rememberUpdatedState(onResult)
    val latestMimeTypes by rememberUpdatedState(mimeTypes)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri != null) latestOnResult(uri)
    }
    return remember(launcher) { { launcher.launch(latestMimeTypes) } }
}
