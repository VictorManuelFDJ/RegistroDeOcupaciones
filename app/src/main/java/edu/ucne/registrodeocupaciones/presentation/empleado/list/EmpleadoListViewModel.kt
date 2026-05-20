package edu.ucne.registrodeocupaciones.presentation.empleado.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.DeleteEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.ObserveEmpleadoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoListViewModel @Inject constructor(
    private val ObserveEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val DeleteEmpleadoUseCase: DeleteEmpleadoUseCase
): ViewModel() {
    private val _state = MutableStateFlow(EmpleadoListUiState(isLoading = true))
    val state: StateFlow<EmpleadoListUiState> = _state.asStateFlow()

    init {
        loadEmpleado()
    }

    fun onEvent(event: EmpleadoListUiEvent){
        when(event){
            EmpleadoListUiEvent.Load -> loadEmpleado()
            EmpleadoListUiEvent.Refresh ->  loadEmpleado()
            is EmpleadoListUiEvent.Delete -> onDelete(event.id)
            is EmpleadoListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message)}
            EmpleadoListUiEvent.ClearMessage -> _state.update { it.copy(message = null)}
            EmpleadoListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true)}
            is EmpleadoListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }
    }

    fun loadEmpleado(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            ObserveEmpleadoUseCase().collectLatest { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        empleado = list,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int){
        viewModelScope.launch { DeleteEmpleadoUseCase(id)}
        onEvent(EmpleadoListUiEvent.ShowMessage("Eliminado"))

    }
}
