package edu.ucne.registrodeocupaciones.domain.ocupacion.useCase

import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Inject

class GetOcupacionesSyncUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke() = repository.getOcupacionesSync()
}
