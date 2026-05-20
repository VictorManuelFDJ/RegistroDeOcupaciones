package edu.ucne.registrodeocupaciones.presentation.ocupacion.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.DeleteOcupacionUseCase
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.ObserveOcupacionesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OcupacionListViewModel @Inject constructor(
    private val ObserveOcupacionUseCase: ObserveOcupacionesUseCase,
    private val DeleteOcupacionUseCase: DeleteOcupacionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(OcupacionListUiState(isLoading = true))
    val state: StateFlow<OcupacionListUiState> = _state.asStateFlow()

    init{
        loadOcupacion()
    }

    fun onEvent(event: OcupacionListUiEvent){
        when(event){
            OcupacionListUiEvent.Load -> loadOcupacion()
            OcupacionListUiEvent.Refresh -> loadOcupacion()
           is OcupacionListUiEvent.Delete -> onDelete(event.id)
           is OcupacionListUiEvent.ShowMessage -> _state.update{it.copy(message = event.message)}
            OcupacionListUiEvent.ClearMessage ->    _state.update{it.copy(message = null)}
            OcupacionListUiEvent.CreateNew -> _state.update{it.copy(navigateToCreate = true)}
            is OcupacionListUiEvent.Edit -> _state.update{it.copy(navigateToEditId = event.id)}
        }
    }

    fun loadOcupacion() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            ObserveOcupacionUseCase().collectLatest { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        ocupacion = list,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int){
        viewModelScope.launch { DeleteOcupacionUseCase(id)
            onEvent(OcupacionListUiEvent.ShowMessage("Eliminado"))
        }
    }

}


