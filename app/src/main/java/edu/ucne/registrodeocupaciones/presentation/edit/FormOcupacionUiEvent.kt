package edu.ucne.registrodeocupaciones.presentation.edit

sealed interface FormOcupacionUiEvent{
    data class Load(val id: Int?) : FormOcupacionUiEvent
    data class DescripcionChanged(val value: String) : FormOcupacionUiEvent
    data class SueldoChanged(val value: String) : FormOcupacionUiEvent
    data object save : FormOcupacionUiEvent
    data object delete : FormOcupacionUiEvent

}