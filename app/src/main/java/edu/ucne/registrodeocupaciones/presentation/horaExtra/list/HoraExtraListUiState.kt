package edu.ucne.registrodeocupaciones.presentation.horaExtra.list

import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra

data class HoraExtraListUiState (
    val isLoading : Boolean = false,
    val horasExtras: List<HoraExtra> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)
