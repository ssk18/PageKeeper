package com.ssk.pagekeeper.feature.library

import androidx.compose.runtime.Composable
import com.ssk.pagekeeper.core.designsystem.ui.rememberFilePicker

/**
 * Library-feature wrapper around `rememberFilePicker` that owns the MIME contract
 * for book imports. Callers receive a stable `launch` lambda and a URI string on
 * selection. Keeps the import contract co-located with the feature that owns it.
 */
@Composable
fun rememberBookPicker(onResult: (uri: String) -> Unit): () -> Unit =
    rememberFilePicker(mimeTypes = arrayOf("*/*")) { uri -> onResult(uri.toString()) }
