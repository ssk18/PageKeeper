package com.ssk.pagekeeper.core.data.repository

import com.ssk.pagekeeper.core.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor() : DataRepository {
    override val data: Flow<List<String>> = flow { emit(listOf("Android")) }
}
