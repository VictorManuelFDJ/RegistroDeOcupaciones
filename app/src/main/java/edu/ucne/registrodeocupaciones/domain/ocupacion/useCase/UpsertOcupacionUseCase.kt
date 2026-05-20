package edu.ucne.registrodeocupaciones.domain.ocupacion.useCase

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class UpsertOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Int>{
        val ocupaciones = repository.getOcupacionesSync()
        val listaActual = ocupaciones
            .filter { it.ocupacionId != ocupacion.ocupacionId }
            .map { it.descripcion }

        val descriptionResult = validateDescription(ocupacion.descripcion, listaActual)

        if(!descriptionResult.isValid){
            return Result.failure(IllegalArgumentException(descriptionResult.error))
        }

        val sueldoResult = validateSueldo(ocupacion.sueldo.toString())
        if(!sueldoResult.isValid){
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }
        return  runCatching { repository.upsert(ocupacion) }
    }
}