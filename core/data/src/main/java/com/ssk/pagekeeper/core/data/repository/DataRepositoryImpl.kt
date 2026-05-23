package com.ssk.pagekeeper.core.data.repository

import com.ssk.pagekeeper.core.domain.repository.DataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor() : DataRepository {
    override val data: Flow<List<String>> = flow { emit(listOf("Android")) }

    // Mock restore: emit Loading, wait, then Ready. Replace with the real persistence
    // load (Room, DataStore, etc.) once storage is wired.
    override val isReady: Flow<Boolean> = flow {
        emit(false)
        delay(METADATA_RESTORE_MOCK_MS)
        emit(true)
    }

    private companion object {
        const val METADATA_RESTORE_MOCK_MS = 1_500L
    }
}
