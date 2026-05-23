package edu.ucne.registrodeocupaciones.domain.empleado.model

import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import java.time.LocalDate


data class Empleado(
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0,
    val frecuenciaDePago: FrecuenciaDePago,
    val ocupacionId: Int = 0
)