package edu.ucne.registrodeocupaciones.presentation.horaExtra.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.ObserveEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.DeleteHoraExtraUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.ObserveHoraExtraUseCase
import edu.ucne.registrodeocupaciones.presentation.empleado.list.EmpleadoListUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoraExtraListViewModel @Inject constructor(
    private val ObserveHoraExtraUseCase: ObserveHoraExtraUseCase,
    private val DeleteHorasExtaUseCase: DeleteHoraExtraUseCase,
    private val observeEmpleadosUseCase: ObserveEmpleadoUseCase
): ViewModel() {
    private val _state = MutableStateFlow(HoraExtraListUiState(isLoading = true))
    val state: StateFlow<HoraExtraListUiState> = _state.asStateFlow()

    init {
        loadHoraExtra()
        loadEmpleados()
    }

    fun onEvent(event: HoraExtraListUiEvent){
        when(event){
            HoraExtraListUiEvent.Load -> loadHoraExtra()
            HoraExtraListUiEvent.Refresh -> loadHoraExtra()
            is HoraExtraListUiEvent.Delete -> onDelete(event.id)
            is HoraExtraListUiEvent.ShowMessage -> _state.update { it.copy(message = event.message)}
            HoraExtraListUiEvent.ClearMessage -> _state.update { it.copy(message = null)}
            HoraExtraListUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true)}
            is HoraExtraListUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
        }

    }

    fun loadHoraExtra(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            ObserveHoraExtraUseCase().collectLatest { list ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        horasExtras = list,
                        message = null
                    )
                }
            }
        }
    }
    private fun loadEmpleados() {
        viewModelScope.launch {
            observeEmpleadosUseCase().collectLatest { lista ->
                _state.update { it.copy(empleados = lista) }
            }
        }
    }

    private fun onDelete(id: Int){
        viewModelScope.launch { DeleteHorasExtaUseCase(id)}
        onEvent(HoraExtraListUiEvent.ShowMessage("Eliminado"))
    }
}