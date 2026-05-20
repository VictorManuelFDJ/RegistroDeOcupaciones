package edu.ucne.registrodeocupaciones.presentation.empleado.list

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado

data class EmpleadoListUiState (
    val isLoading : Boolean = false,
    val empleado: List<Empleado> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)