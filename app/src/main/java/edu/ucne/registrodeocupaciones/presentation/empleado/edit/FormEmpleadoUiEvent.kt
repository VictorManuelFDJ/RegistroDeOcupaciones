package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import java.time.LocalDate

sealed interface FormEmpleadoUiEvent {
    data class NombreChanged(val value: String) : FormEmpleadoUiEvent
    data class SexoChanged(val value: String) : FormEmpleadoUiEvent
    data class SueldoChanged(val value: String) : FormEmpleadoUiEvent
    data class FechaIngresoChanged(val value: LocalDate) : FormEmpleadoUiEvent
    data object Save : FormEmpleadoUiEvent
    data object Delete : FormEmpleadoUiEvent
}
