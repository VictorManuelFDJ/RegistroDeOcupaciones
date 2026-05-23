package edu.ucne.registrodeocupaciones.data.empleado.mapper

import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosEntity
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado

fun EmpleadosEntity.toDomain(): Empleado = Empleado(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombre = nombre,
    sexo = sexo,
    sueldo =sueldo,
    frecuenciaDePago = frecuenciaDePago,
    ocupacionId = ocupacionId
)

fun Empleado.toEntity(): EmpleadosEntity = EmpleadosEntity(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombre = nombre,
    sexo = sexo,
    sueldo = sueldo,
    frecuenciaDePago = frecuenciaDePago,
    ocupacionId =ocupacionId

)