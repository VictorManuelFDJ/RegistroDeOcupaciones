package edu.ucne.registrodeocupaciones.domain.horasExtra.useCase

import edu.ucne.registrodeocupaciones.domain.horasExtra.repository.HoraExtraRepository
import javax.inject.Inject

class GetHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
){
    suspend operator fun invoke(id: Int ) = repository.getHoraExtra(id)
}