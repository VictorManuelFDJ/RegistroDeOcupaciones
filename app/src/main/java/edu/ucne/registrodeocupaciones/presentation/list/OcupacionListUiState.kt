package edu.ucne.registrodeocupaciones.presentation.list

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion

data class OcupacionListUiState(
    val isLoading : Boolean = false,
    val ocupacion: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)