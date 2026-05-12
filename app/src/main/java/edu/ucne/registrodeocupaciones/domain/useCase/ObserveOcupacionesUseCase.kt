package edu.ucne.registrodeocupaciones.domain.useCase

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOcupacionesUseCase @Inject constructor (
    private val repository: OcupacionRepository
){
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupaciones()
}


