package edu.ucne.registrodeocupaciones.domain.useCase

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpsertOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Int>{
        val ocupaciones = repository.observeOcupaciones().first()
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