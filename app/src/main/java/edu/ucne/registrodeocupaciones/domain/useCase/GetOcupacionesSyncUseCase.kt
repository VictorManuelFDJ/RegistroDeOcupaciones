package edu.ucne.registrodeocupaciones.domain.useCase

import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import jakarta.inject.Inject

class GetOcupacionesSyncUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(): List<Ocupacion> {
        return repository.getOcupacionesSync()
    }
}