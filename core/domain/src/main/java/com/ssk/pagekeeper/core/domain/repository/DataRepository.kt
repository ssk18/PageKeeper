package com.ssk.pagekeeper.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataRepository {
    val data: Flow<List<String>>

    /**
     * Emits `false` while saved book metadata is being restored, then `true` once the
     * restore completes. Drives the splash screen.
     */
    val isReady: Flow<Boolean>
}
