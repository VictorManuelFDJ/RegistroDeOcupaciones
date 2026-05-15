package edu.ucne.registrodeocupaciones.domain.ocupacion.useCase

import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class GetOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id: Int) = repository.getOcupacion(id)
}