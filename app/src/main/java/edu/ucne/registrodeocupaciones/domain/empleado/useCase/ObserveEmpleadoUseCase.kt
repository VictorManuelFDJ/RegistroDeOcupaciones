package edu.ucne.registrodeocupaciones.domain.empleado.useCase

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class  ObserveEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
){
    operator fun invoke(): Flow<List<Empleado>> = repository.observeEmpleado()
}