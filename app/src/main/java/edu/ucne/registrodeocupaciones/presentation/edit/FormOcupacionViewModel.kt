package edu.ucne.registrodeocupaciones.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.useCase.DeleteOcupacionUseCase
import edu.ucne.registrodeocupaciones.domain.useCase.GetOcupacionUseCase
import edu.ucne.registrodeocupaciones.domain.useCase.ObserveOcupacionesUseCase
import edu.ucne.registrodeocupaciones.domain.useCase.UpsertOcupacionUseCase
import edu.ucne.registrodeocupaciones.domain.useCase.validateDescription
import edu.ucne.registrodeocupaciones.domain.useCase.validateSueldo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.serialization.Serializable

@Serializable
data class FormOcupacionScreen(val ocupacionId: Int)

@HiltViewModel
class FormOcupacionViewModel @Inject constructor(
    private val getOcupacionUseCase: GetOcupacionUseCase,
    private val observeOcupacionesUseCase: ObserveOcupacionesUseCase,
    private val upsertOcupacionUseCase: UpsertOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val routerArgs = savedStateHandle.toRoute<FormOcupacionScreen>()
    private val ocupacionId: Int  = routerArgs.ocupacionId

    private val _state = MutableStateFlow(FormOcupacionUiState())
    val state: StateFlow<FormOcupacionUiState> = _state.asStateFlow()

    init{
        loadOcupacion(ocupacionId)
    }

    fun onEvent (event: FormOcupacionUiEvent)
    {
        when(event){
            is FormOcupacionUiEvent.Load -> loadOcupacion(event.id)
            is FormOcupacionUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }
            is FormOcupacionUiEvent.SueldoChanged -> _state.update {
                it.copy(sueldo = event.value, sueldoError = null)
            }

            FormOcupacionUiEvent.save -> onSave()
            FormOcupacionUiEvent.delete -> onDelete()
        }
    }

    private fun loadOcupacion(id: Int?){
        if(id == null || id == 0 ) {
            _state.update { it.copy(isNew = true, ocupacionId = null) }
            return
        }

        viewModelScope.launch{
            val ocupacion = getOcupacionUseCase(id)
            if(ocupacion != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        ocupacionId = ocupacion.ocupacionId,
                        descripcion = ocupacion.descripcion,
                        sueldo = ocupacion.sueldo.toString()
                    )
                }
            }else{
                _state.update { it.copy(isNew = true, ocupacionId = null) }
            }
        }

    }

    private fun onSave(){
        viewModelScope.launch {
            val ocupaciones = observeOcupacionesUseCase().first()
            val descripcionesExistentes = ocupaciones
                .filter { it.ocupacionId != state.value.ocupacionId }
                .map { it.descripcion }

            val descripcion = state.value.descripcion
            val descripcionValidation = validateDescription(descripcion, descripcionesExistentes)
            val sueldoValidation = validateSueldo(state.value.sueldo)

            if(!descripcionValidation.isValid || !sueldoValidation.isValid){
                _state.update{
                    it.copy(
                        descripcionError = descripcionValidation.error,
                        sueldoError = sueldoValidation.error
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }

            val ocupacion = Ocupacion(
                ocupacionId = state.value.ocupacionId ?: 0,
                descripcion = descripcion,
                sueldo = state.value.sueldo.toDouble()
            )

            val result = upsertOcupacionUseCase(ocupacion)
            result.onSuccess{newId ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true,
                        ocupacionId = newId,
                        isNew = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }
    private fun onDelete(){
        val id = state.value.ocupacionId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteOcupacionUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }

}
