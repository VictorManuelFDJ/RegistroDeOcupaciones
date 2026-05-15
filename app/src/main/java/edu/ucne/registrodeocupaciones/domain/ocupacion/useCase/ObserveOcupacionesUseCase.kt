package edu.ucne.registrodeocupaciones.domain.ocupacion.useCase

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveOcupacionesUseCase @Inject constructor (
    private val repository: OcupacionRepository
){
    operator fun invoke(): Flow<List<Ocupacion>> = repository.observeOcupaciones()
}


