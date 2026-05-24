package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

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
        horas > 24 -> HoraExtraValidation(false,
            "El dia solo tiene 24 horas, verifica la cantidad ")
        else -> HoraExtraValidation(true)
    }
}

fun validateRecargo(recargoHoras: String): HoraExtraValidation{
    val recargo = recargoHoras.toDoubleOrNull()
    return when{
        recargoHoras.isBlank() -> HoraExtraValidation(false,
            "El recargo no puede esta vacio")
        recargo == null -> HoraExtraValidation(false,
            "Ingresa un monto de recargo válido")
        recargo < 0.0 -> HoraExtraValidation(false,
            "El recargo no puede ser negativo")
        else -> HoraExtraValidation(true)
    }
}