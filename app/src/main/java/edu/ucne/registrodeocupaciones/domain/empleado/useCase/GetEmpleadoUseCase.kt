package edu.ucne.registrodeocupaciones.domain.empleado.useCase

import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import javax.inject.Inject

class GetEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
){
    suspend operator fun invoke(id: Int ) = repository.getEmpleado(id)
}