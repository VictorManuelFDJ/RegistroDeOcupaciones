package edu.ucne.registrodeocupaciones.domain.empleado.model

import java.time.LocalDate


data class Empleado(
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0,
)