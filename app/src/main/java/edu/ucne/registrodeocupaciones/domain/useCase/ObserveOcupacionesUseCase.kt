package edu.ucne.registrodeocupaciones.domain.useCase

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow

class ObserveOcupacionesUseCase (
    private val repository: OcupacionRepository
){
    suspend operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupaciones()
}


