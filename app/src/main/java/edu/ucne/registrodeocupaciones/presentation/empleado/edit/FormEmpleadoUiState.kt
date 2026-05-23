package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity
import java.time.LocalDate

data class FormEmpleadoUiState(
    val empleadoId: Int? = null,
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: String = "",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val frecuenciaDePago: FrecuenciaDePago? = null,
    val ocupacionId: Int? = null,
    val ocupacionDisponible: List<OcupacionEntity> = emptyList(),
    //-----------------------------------------------------------
    val nombreError: String? = null,
    val sexoError: String? = null,
    val sueldoError: String? = null,
    val fechaError: String? = null,
    val ocupacionError: String? = null,
    val frecuenciaDePagoError: String? = null,
    //------------------------------------------------------------
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false

)