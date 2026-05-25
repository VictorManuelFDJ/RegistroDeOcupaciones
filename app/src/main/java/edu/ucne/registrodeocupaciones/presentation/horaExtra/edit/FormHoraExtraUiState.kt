package edu.ucne.registrodeocupaciones.presentation.horaExtra.edit

import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import java.time.LocalDate

data class FormHoraExtraUiState(
    val horaExtraId: Int? = null,
    val empleadoId: Int? = null,
    val fecha: LocalDate = LocalDate.now(),
    val cantidadDeHora: String = "",
    val tipoHoraExtra: TipoHoraExtra? = null,
    val recargo: Double = 0.0,

    val empleadoDisponibles: List<Empleado> = emptyList(),
    val ocupacionDisponible: List<Ocupacion> = emptyList(),

    val empleadoError: String? = null,
    val fechaError: String? = null,
    val cantidadDeHoraError: String? = null,
    val tipoHoraExtraError: String? = null,
    val recargoError: String? = null,

    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false

)