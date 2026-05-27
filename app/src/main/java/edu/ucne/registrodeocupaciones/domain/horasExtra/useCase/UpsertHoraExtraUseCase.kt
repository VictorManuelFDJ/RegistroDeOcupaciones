package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import edu.ucne.registrodeocupaciones.domain.horasExtra.repository.HoraExtraRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpsertHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
){
    suspend operator fun invoke(horaExtra : HoraExtra): Result<Int>{

        val empleadoResult = validateEmpleadoId(horaExtra.empleadoId)
        if(!empleadoResult.isValid){
            return Result.failure(IllegalArgumentException(empleadoResult.error))
        }

        val fechaResult = validateFechaHora(horaExtra.fecha)
        if(!fechaResult.isValid){
            return Result.failure(IllegalArgumentException(fechaResult.error))
        }

        val cantidadResult = validateCantidadHora(horaExtra.cantidadHoras.toString())
        if(!cantidadResult.isValid){
            return Result.failure(IllegalArgumentException(cantidadResult.error))
        }

        return runCatching { repository.upsert(horaExtra) }
    }
}