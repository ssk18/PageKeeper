package com.ssk.pagekeeper.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataRepository {
    val data: Flow<List<String>>
}
