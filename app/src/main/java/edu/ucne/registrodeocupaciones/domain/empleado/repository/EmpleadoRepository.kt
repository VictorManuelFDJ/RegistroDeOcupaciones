package edu.ucne.registrodeocupaciones.domain.empleado.repository

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import kotlinx.coroutines.flow.Flow

interface EmpleadoRepository{
    fun observeEmpleado(): Flow<List<Empleado>>
    suspend fun getEmpleado(id: Int): Empleado?
    suspend fun upsert(empleado: Empleado): Int
    suspend fun delete(id: Int)
    suspend fun exists(id: Int): Boolean
}