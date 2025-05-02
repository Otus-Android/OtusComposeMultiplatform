package ru.otus.cmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BarcodesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BarcodesUiState())
    val uiState: StateFlow<BarcodesUiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<BarcodesSideEffect>(Channel.BUFFERED)
    val sideEffect: Flow<BarcodesSideEffect> = _sideEffect.receiveAsFlow()

    private val detectionFlow = MutableSharedFlow<String>()
    private var job: Job? = null

    init {
        detectionFlow
            .distinctUntilChanged()
            .filter { it.isNotEmpty() }
            .onEach { value ->
                _uiState.update { it.copy(it.items + value) }
                _sideEffect.send(BarcodesSideEffect.PopBackStack)
            }
            .launchIn(viewModelScope)
    }

    fun onBarcodeScanned(value: String) {
        if (job?.isActive == true) return

        job = viewModelScope.launch {
            detectionFlow.emit(value)
            delay(300)
            detectionFlow.emit("")
        }
    }
}

data class BarcodesUiState(
    val items: List<String> = emptyList()
)

sealed interface BarcodesSideEffect {
    data object PopBackStack : BarcodesSideEffect
}