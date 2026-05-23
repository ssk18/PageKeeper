package com.ssk.pagekeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssk.pagekeeper.core.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    dataRepository: DataRepository,
) : ViewModel() {
    val isReady: StateFlow<Boolean> = dataRepository.isReady
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
