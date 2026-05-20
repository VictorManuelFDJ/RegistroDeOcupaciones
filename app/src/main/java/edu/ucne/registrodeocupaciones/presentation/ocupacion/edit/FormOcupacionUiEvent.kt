package edu.ucne.registrodeocupaciones.presentation.ocupacion.edit

sealed interface FormOcupacionUiEvent {
    data class Load(val id: Int?) : FormOcupacionUiEvent
    data class DescripcionChanged(val value: String) : FormOcupacionUiEvent
    data class SueldoChanged(val value: String) : FormOcupacionUiEvent
    data object Save : FormOcupacionUiEvent
    data object Delete : FormOcupacionUiEvent
}
