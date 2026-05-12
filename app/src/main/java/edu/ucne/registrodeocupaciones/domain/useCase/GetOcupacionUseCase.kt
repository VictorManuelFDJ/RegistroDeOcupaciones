package edu.ucne.registrodeocupaciones.domain.useCase

import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import javax.inject.Inject

class GetOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(id: Int) = repository.getOcupacion(id)
}