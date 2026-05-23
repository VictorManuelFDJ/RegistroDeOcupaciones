package edu.ucne.registrodeocupaciones.data.horasExtra.mapper


import edu.ucne.registrodeocupaciones.data.horasExtra.local.HorasExtraEntity
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra

fun HorasExtraEntity.toDomain(): HoraExtra = HoraExtra(
   horaExtraId = horaExtraId,
   empleadoId = empleadoId,
    fecha = fecha,
   tipoHoraExtra = tipoHoraExtra,
   cantidadHoras = cantidadHoras,
   recargo =  recargo,
   esPuestoDireccion = esPuestoDireccion
)

fun HoraExtra.toEntity(): HorasExtraEntity = HorasExtraEntity(
    horaExtraId = horaExtraId,
    fecha = fecha,
    empleadoId = empleadoId,
    tipoHoraExtra = tipoHoraExtra,
    cantidadHoras = cantidadHoras,
    recargo =  recargo,
    esPuestoDireccion = esPuestoDireccion
)