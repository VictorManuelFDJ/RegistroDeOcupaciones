package edu.ucne.registrodeocupaciones.presentation.horaExtra.edit

import java.time.LocalDate

data class FormHoraExtraUiState(
    val horaExtraId: Int? = null,
    val empleadoId: Int? = null,
    val fecha: LocalDate = LocalDate.now(),

)