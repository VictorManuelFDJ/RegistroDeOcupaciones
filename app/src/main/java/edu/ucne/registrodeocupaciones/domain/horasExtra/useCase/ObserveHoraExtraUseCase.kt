package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import edu.ucne.registrodeocupaciones.domain.horasExtra.repository.HoraExtraRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
){
    operator fun invoke(): Flow<List<HoraExtra>> = repository.observeHoraExtra()
}