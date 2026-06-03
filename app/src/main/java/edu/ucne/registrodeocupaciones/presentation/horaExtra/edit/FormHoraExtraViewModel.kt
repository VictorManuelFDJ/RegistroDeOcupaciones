package edu.ucne.registrodeocupaciones.presentation.horaExtra.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.data.horasExtra.local.HorasExtraEntity
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.ObserveEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.DeleteHoraExtraUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.GetHoraExtraUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.ObserveHoraExtraUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.UpsertHoraExtraUseCase
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.calcularMontoHoraExtra
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.validateCantidadHora
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.validateEmpleadoId
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.validateFechaHora
import edu.ucne.registrodeocupaciones.domain.horasExtra.useCase.validateTipoHora
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.ObserveOcupacionesUseCase
import edu.ucne.registrodeocupaciones.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormHoraExtraViewModel @Inject constructor(
    private val getHoraExtraUseCase: GetHoraExtraUseCase,
    private val upsertHoraExtraUseCase: UpsertHoraExtraUseCase,
    private val observeOcupacionesUseCase: ObserveOcupacionesUseCase,
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase,
    private val deleteHoraExtraUseCase: DeleteHoraExtraUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _state = MutableStateFlow(FormHoraExtraUiState())
    val state: StateFlow<FormHoraExtraUiState> = _state.asStateFlow()


    init {
        loadEmpleadosYOcupaciones()
    }

    fun onEvent(event: FormHoraExtraUiEvent){
         when(event){
             is FormHoraExtraUiEvent.EmpleadoIdChange -> _state.update {
                 it.copy(empleadoId = event.value, empleadoError = null) }
             is FormHoraExtraUiEvent.FechaChange -> _state.update {
                 it.copy(fecha = event.value, fechaError = null)}
             is FormHoraExtraUiEvent.CantidadHoraChange -> _state.update {
                 it.copy(cantidadDeHora = event.value ?: "", cantidadDeHoraError = null) }
             is FormHoraExtraUiEvent.TipoHoraExtraChange -> _state.update {
                 it.copy(tipoHoraExtra = event.value, tipoHoraExtraError = null) }
             FormHoraExtraUiEvent.Save -> onSaved()
             FormHoraExtraUiEvent.Delete -> onDelete()
         }
    }


    private fun loadEmpleadosYOcupaciones(){
        viewModelScope.launch {
            observeEmpleadoUseCase().collectLatest { lista ->
                _state.update { it.copy(empleadoDisponibles = lista) }
            }
        }
        viewModelScope.launch {
            observeOcupacionesUseCase().collectLatest { lista ->
                _state.update { it.copy(ocupacionDisponible = lista) }
            }
        }
    }

    fun loadHoraExtra(id: Int){
        if(id == 0 ){
            val empleados = _state.value.empleadoDisponibles
            val ocupaciones = _state.value.ocupacionDisponible
            _state.value = FormHoraExtraUiState(
                empleadoDisponibles = empleados,
                ocupacionDisponible = ocupaciones
            )
            return
        }
        viewModelScope.launch {
            val horaExtra = getHoraExtraUseCase(id)
            if(horaExtra != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        horaExtraId = horaExtra.horaExtraId,
                        empleadoId = horaExtra.empleadoId,
                        fecha = horaExtra.fecha,
                        cantidadDeHora = horaExtra.cantidadHoras.toString(),
                        tipoHoraExtra =  horaExtra.tipoHoraExtra,
                        recargo = horaExtra.recargo

                    )
                }
            }else{
                _state.update { it.copy(isNew = true, horaExtraId = null) }
            }
        }
    }

    private fun onSaved(){
        if (_state.value.isSaving) return
        viewModelScope.launch {
            val empleadoValidation = validateEmpleadoId(state.value.empleadoId ?: 0 )
            val fechaValidation = validateFechaHora(state.value.fecha)
            val cantidadHoraValidation = validateCantidadHora(state.value.cantidadDeHora)
            val tipoHoraExtra = state.value.tipoHoraExtra

            val tipoValidation = validateTipoHora(
                tipoSelecionado = state.value.tipoHoraExtra,
                horasInput = state.value.cantidadDeHora
            )

            if (!empleadoValidation.isValid
                || !fechaValidation.isValid
                || !cantidadHoraValidation.isValid
                || !tipoValidation.isValid){
                _state.update { it.copy(
                    empleadoError = empleadoValidation.error,
                    fechaError = fechaValidation.error,
                    cantidadDeHoraError = cantidadHoraValidation.error,
                    tipoHoraExtraError = tipoValidation.error
                ) }
                return@launch
            }
            val empleado = state.value.empleadoDisponibles.
            find { it.empleadoId == state.value.empleadoId }
            val ocupacion = state.value.ocupacionDisponible.
            find { it.ocupacionId == empleado?.ocupacionId }
            if (empleado != null && ocupacion != null && tipoHoraExtra != null){
                _state.update { it.copy(isSaving = true) }

                val dineroAPagar = calcularMontoHoraExtra(
                    sueldo = empleado.sueldo,
                    frecuenciaDePago = empleado.frecuenciaDePago,
                    tipoHoraExtra = tipoHoraExtra,
                    cantidadHoras = state.value.cantidadDeHora.toInt(),
                    esPuestoDireccion = ocupacion.esPuestoDireccion
                )
                val horaExtra = HoraExtra(
                    horaExtraId = state.value.horaExtraId ?: 0,
                    empleadoId = empleado.empleadoId,
                    fecha = state.value.fecha,
                    cantidadHoras = state.value.cantidadDeHora.toInt(),
                    tipoHoraExtra = tipoHoraExtra,
                    recargo = dineroAPagar,
                    esPuestoDireccion = ocupacion.esPuestoDireccion
                )

                val result = upsertHoraExtraUseCase(horaExtra)

                result.onSuccess { _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            cantidadDeHoraError = error.message
                        )
                    }
                }
            }
        }
    }
    private fun onDelete(){
        val id = state.value.horaExtraId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteHoraExtraUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }

}