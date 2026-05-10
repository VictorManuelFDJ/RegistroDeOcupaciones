package edu.ucne.registrodeocupaciones.data.mapper

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.data.local.OcupacionEntity

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