package edu.ucne.registrodeocupaciones.domain.empleado.useCase

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
){
    suspend operator fun invoke(empleado: Empleado): Result<Int>{
        val listaActual = repository.observeEmpleado().first()
            .filter { it.empleadoId != empleado.empleadoId }
            .map { it.nombre }

        val nombreResult = validateNombre(empleado.nombre, listaActual)
        if(!nombreResult.isValid){
            return Result.failure(IllegalArgumentException(nombreResult.error))
        }

        val fechaResult = validateFecha(empleado.fechaIngreso)
        if(!fechaResult.isValid){
            return Result.failure(IllegalArgumentException(fechaResult.error))
        }

        val sexoResult = validateSexo(empleado.sexo)
        if(!sexoResult.isValid){
            return Result.failure(IllegalArgumentException(sexoResult.error))
        }

        val sueldoResult = validateSueldoE(empleado.sueldo.toString())
        if(!sueldoResult.isValid){
            return Result.failure(IllegalArgumentException(sueldoResult.error))
        }
        return runCatching{repository.upsert(empleado)}

    }
}
