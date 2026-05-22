package com.ssk.pagekeeper.core.data.repository

import com.ssk.pagekeeper.core.domain.repository.DataRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class DataRepositoryImpl @Inject constructor() : DataRepository {
    override val data: Flow<List<String>> = flow { emit(listOf("Android")) }
}
