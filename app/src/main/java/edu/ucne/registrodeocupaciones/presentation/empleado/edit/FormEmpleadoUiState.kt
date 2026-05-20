package edu.ucne.registrodeocupaciones.presentation.empleado.edit

import java.time.LocalDate

data class FormEmpleadoUiState(
    val empleadoId: Int? = null,
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: String = "",
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombreError: String? = null,
    val sexoError: String? = null,
    val sueldoError: String? = null,
    val fechaError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false

)