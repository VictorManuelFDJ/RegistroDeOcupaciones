package edu.ucne.registrodeocupaciones.presentation.horaExtra.list

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra

data class HoraExtraListUiState (
    val isLoading : Boolean = false,
    val horasExtras: List<HoraExtra> = emptyList(),
    val empleados: List<Empleado> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null,
    val error: String? = null
)
