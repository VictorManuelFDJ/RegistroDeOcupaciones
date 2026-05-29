package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.DeleteEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.GetEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.UpsertEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.validateFecha
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.validateNombre
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.validateSexo
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.validateSueldoE
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.ObserveOcupacionesUseCase
import kotlinx.coroutines.flow.collectLatest
import edu.ucne.registrodeocupaciones.presentation.navigation.Screen
import edu.ucne.registrodeocupaciones.presentation.ocupacion.edit.FormOcupacionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class FormEmpleadoViewModel @Inject constructor(
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    private val observeOcupacionesUseCase: ObserveOcupacionesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val  _state = MutableStateFlow(FormEmpleadoUiState())
    val state: StateFlow<FormEmpleadoUiState> = _state.asStateFlow()

    init {
        loadOcupacion()
    }

    fun onEvent(event: FormEmpleadoUiEvent){
        when(event){
            is FormEmpleadoUiEvent.NombreChanged -> _state.update {
                it.copy(nombre = event.value, nombreError = null)}
            is FormEmpleadoUiEvent.SexoChanged -> _state.update {
                it.copy(sexo = event.value, sexoError = null)}
            is FormEmpleadoUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)}
            is FormEmpleadoUiEvent.FechaIngresoChanged -> _state.update {
                it.copy(fechaIngreso = event.value, fechaError = null)}
            is FormEmpleadoUiEvent.FrecuenciaDePagoChanged -> _state.update {
                it.copy(frecuenciaDePago = event.value, frecuenciaDePagoError = null)
            }
            is FormEmpleadoUiEvent.OcupacionIdChanged -> _state.update {
                it.copy(ocupacionId = event.value, ocupacionError = null)
            }
            FormEmpleadoUiEvent.Save -> onSave()
            FormEmpleadoUiEvent.Delete -> onDelete()
        }
    }
    fun nuevoRegistro() {
        _state.value = FormEmpleadoUiState()
    }
    private fun loadOcupacion(){
        viewModelScope.launch {
            observeOcupacionesUseCase().collectLatest { listaOcupacion ->
                _state.update { it.copy(ocupacionDisponible = listaOcupacion ) }
            }
        }
    }
    fun loadEmpleado(id: Int){
        if( id == 0 ){

            val ocupaciones = _state.value.ocupacionDisponible
            _state.value = FormEmpleadoUiState(ocupacionDisponible = ocupaciones)
            return
        }
        viewModelScope.launch {
            val empleado = getEmpleadoUseCase(id)
            if(empleado != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadoId,
                        nombre = empleado.nombre,
                        sexo = empleado.sexo,
                        sueldo = empleado.sueldo.toString(),
                        fechaIngreso = empleado.fechaIngreso,
                        frecuenciaDePago = empleado.frecuenciaDePago,
                        ocupacionId = empleado.ocupacionId
                    )
                }
            }else{
                _state.update { it.copy(isNew = true, empleadoId = null) }
            }
        }
    }
    private fun onSave(){
        if (_state.value.isSaving) return
        viewModelScope.launch {
           val nombreValidation = validateNombre(state.value.nombre)
            val sexoValidation = validateSexo(state.value.sexo)
            val sueldoValidation = validateSueldoE(state.value.sueldo)
            val fechaIngresoValidation = validateFecha(state.value.fechaIngreso)
            if (!nombreValidation.isValid
                || !sexoValidation.isValid
                || !sueldoValidation.isValid
                || !fechaIngresoValidation.isValid){
                _state.update {
                    it.copy(
                        nombreError = nombreValidation.error,
                        sexoError = sexoValidation.error,
                        sueldoError = sueldoValidation.error,
                        fechaError = fechaIngresoValidation.error
                    )
                }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }
            val empleado = Empleado(
                empleadoId = state.value.empleadoId ?: 0,
                nombre = state.value.nombre.trim(),
                sexo = state.value.sexo.trim(),
                sueldo = state.value.sueldo.toDouble(),
                fechaIngreso = state.value.fechaIngreso,
                frecuenciaDePago = state.value.frecuenciaDePago,
                ocupacionId = state.value.ocupacionId ?: 0

            )
            val result = upsertEmpleadoUseCase(empleado)
            result.onSuccess { newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        empleadoId = newId,
                        isNew = false
                    )
                }

            }.onFailure { error ->
                _state.update { it.copy(
                    isSaving = false,
                    nombreError = error.message
                ) }
            }
        }
    }

    private fun onDelete(){
        val id = state.value.empleadoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}