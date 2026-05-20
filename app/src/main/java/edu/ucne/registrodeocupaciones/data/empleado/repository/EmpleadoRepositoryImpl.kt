package edu.ucne.registrodeocupaciones.data.empleado.repository

import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosDao
import edu.ucne.registrodeocupaciones.data.empleado.mapper.toDomain
import edu.ucne.registrodeocupaciones.data.empleado.mapper.toEntity
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.sql.DataSource

class EmpleadoRepositoryImpl @Inject constructor(
    private val localDataSource: EmpleadosDao
): EmpleadoRepository{
    override fun observeEmpleado(): Flow<List<Empleado>> {
        return localDataSource.observeAll().map{entities ->
            entities.map{it.toDomain()}}
    }

    override suspend fun getEmpleado(id: Int): Empleado?{
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        return localDataSource.exists(id)
    }

    override suspend fun upsert(empleado: Empleado): Int {
        return localDataSource.upsert(empleado.toEntity()).toInt()
    }
}