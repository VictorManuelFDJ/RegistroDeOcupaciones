package edu.ucne.registrodeocupaciones.domain.ocupacion.useCase

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import jakarta.inject.Inject

class GetOcupacionesSyncUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    suspend operator fun invoke(): List<Ocupacion> {
        return repository.getOcupacionesSync()
    }
}