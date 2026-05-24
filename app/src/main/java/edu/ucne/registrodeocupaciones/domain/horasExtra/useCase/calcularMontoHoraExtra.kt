package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import edu.ucne.registrodeocupaciones.data.horasExtra.local.TipoHoraExtra

fun calcularMontoHoraExtra(
    sueldo: Double,
    frecuenciaDePago: FrecuenciaDePago,
    tipoHoraExtra: TipoHoraExtra,
    cantidadHoras: Int,
    esPuestoDireccion: Boolean

): Double{
    if (esPuestoDireccion){
        return 0.0
    }

    val salarioDiario = sueldo/frecuenciaDePago.divisor

    val valorHoraOrdinaria = salarioDiario / 8.0
    val montoTotal = valorHoraOrdinaria * tipoHoraExtra.factor * cantidadHoras

    return Math.round(montoTotal * 100) / 100.0
}