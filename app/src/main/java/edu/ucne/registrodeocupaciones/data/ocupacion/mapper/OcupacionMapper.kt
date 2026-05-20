package edu.ucne.registrodeocupaciones.data.ocupacion.mapper

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity

fun OcupacionEntity.toDomain(): Ocupacion = Ocupacion(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)
fun Ocupacion.toEntity(): OcupacionEntity = OcupacionEntity(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)