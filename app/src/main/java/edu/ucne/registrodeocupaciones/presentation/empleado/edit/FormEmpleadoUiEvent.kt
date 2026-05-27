package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import java.time.LocalDate

sealed interface FormEmpleadoUiEvent {
    data class NombreChanged(val value: String) : FormEmpleadoUiEvent
    data class SexoChanged(val value: String) : FormEmpleadoUiEvent
    data class SueldoChanged(val value: String) : FormEmpleadoUiEvent
    data class FechaIngresoChanged(val value: LocalDate) : FormEmpleadoUiEvent
    data class FrecuenciaDePagoChanged(val value: FrecuenciaDePago) : FormEmpleadoUiEvent
    data class OcupacionIdChanged(val value: Int) : FormEmpleadoUiEvent
    data object Save : FormEmpleadoUiEvent
    data object Delete : FormEmpleadoUiEvent
}
