package edu.ucne.registrodeocupaciones.presentation.horaExtra.edit

import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra
import java.time.LocalDate

sealed interface FormHoraExtraUiEvent{
    data class EmpleadoIdChange(val value: Int): FormHoraExtraUiEvent
    data class FechaChange(val value: LocalDate): FormHoraExtraUiEvent
    data class CantidadHoraChange(val value: String?): FormHoraExtraUiEvent
    data class TipoHoraExtraChange(val value: TipoHoraExtra): FormHoraExtraUiEvent
    data object Save : FormHoraExtraUiEvent
    data object Delete : FormHoraExtraUiEvent
}