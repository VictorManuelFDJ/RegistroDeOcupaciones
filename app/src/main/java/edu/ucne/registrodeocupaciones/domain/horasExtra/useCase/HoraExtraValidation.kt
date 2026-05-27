package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.EmpleadoValidation

data class HoraExtraValidation(
    val isValid: Boolean,
    val error: String? = null
)

fun validateEmpleadoId(empleadoId: Int): HoraExtraValidation{
    return when{
        empleadoId <=0 -> HoraExtraValidation(false,
            "Selecione un empleado valido de la lista")
        else -> HoraExtraValidation(true)
    }
}

fun validateFechaHora(fecha: java.time.LocalDate): EmpleadoValidation{
    val fechaActual = java.time.LocalDate.now()
    return when{
        fecha.isAfter(fechaActual) -> EmpleadoValidation(false,
            "La fecha de ingreso no puede ser una fecha futura")
        fecha.isBefore(java.time.LocalDate.of(2000,1,1)) -> EmpleadoValidation(false,
            "La fecha de ingreso no puede ser anterior al año 2000")
        else -> EmpleadoValidation(true)
    }
}

fun validateCantidadHora(horasCantidad: String): HoraExtraValidation{
    val horas = horasCantidad.toIntOrNull()
    return when{
        horasCantidad.isBlank() -> HoraExtraValidation(false,
            "La cantidad de hora es necesaria")
        horas == null -> HoraExtraValidation(false,
            "Ingresa un numero valido para la cantidad de horas")
        horas <= 0 -> HoraExtraValidation(false,
            "La cantidad de hora tiene que ser mayor de cero ")
        else -> HoraExtraValidation(true)
    }
}

fun validateTipoHora(
    tipoSelecionado: TipoHoraExtra?,
    horasInput: String,
    horasAcumuladasSemana: Int = 0
): HoraExtraValidation {
    val horasNuevas = horasInput.toIntOrNull() ?: return HoraExtraValidation(false, "Ingrese una cantidad válida")
    val totalHoras = horasNuevas + horasAcumuladasSemana
    val limiteHorasExtrasNormales = 24

    return when {
        tipoSelecionado == null -> HoraExtraValidation(
            isValid = false,
            error = "Debe seleccionar un tipo de hora extra."
        )
        horasNuevas <= 0 -> HoraExtraValidation(
            isValid = false,
            error = "La cantidad de horas debe ser mayor a 0."
        )
        horasNuevas >= 24 -> HoraExtraValidation(
            isValid = false,
            error = "Es físicamente imposible registrar 24h o más en un solo día."
        )
        totalHoras > limiteHorasExtrasNormales && tipoSelecionado != TipoHoraExtra.ALTA_VOLUMEN -> {
            HoraExtraValidation(
                isValid = false,
                error = "Al pasar de 24h extras (68h totales), la ley exige usar 'Alto Volumen'."
            )
        }
        (tipoSelecionado == TipoHoraExtra.DIURNA || tipoSelecionado == TipoHoraExtra.NOCTURNA) && horasNuevas > 8 -> {
            HoraExtraValidation(
                isValid = false,
                error = "Límite excedido. Máximo 8 horas extras regulares por día."
            )
        }
        tipoSelecionado == TipoHoraExtra.FERIADO && horasNuevas > 12 -> {
            HoraExtraValidation(
                isValid = false,
                error = "Para evitar agotamiento, un día feriado trabajado no debe exceder 12h."
            )
        }
        tipoSelecionado == TipoHoraExtra.ALTA_VOLUMEN && horasNuevas > 12 -> {
            HoraExtraValidation(
                isValid = false,
                error = "Límite de Alto Volumen excedido. Máximo 12h permitidas por día."
            )
        }
        else -> HoraExtraValidation(true)
    }
}
