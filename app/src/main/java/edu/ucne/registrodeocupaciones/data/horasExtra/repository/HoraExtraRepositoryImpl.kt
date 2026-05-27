package edu.ucne.registrodeocupaciones.data.horasExtra.repository

import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosDao
import edu.ucne.registrodeocupaciones.data.empleado.mapper.toDomain
import edu.ucne.registrodeocupaciones.data.empleado.mapper.toEntity
import edu.ucne.registrodeocupaciones.data.horasExtra.local.HorasExtraDao
import edu.ucne.registrodeocupaciones.data.horasExtra.mapper.toDomain
import edu.ucne.registrodeocupaciones.data.horasExtra.mapper.toEntity
import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import edu.ucne.registrodeocupaciones.domain.horasExtra.repository.HoraExtraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HoraExtraRepositoryImpl @Inject constructor(
    private val localDataSource: HorasExtraDao
): HoraExtraRepository{
    override fun observeHoraExtra(): Flow<List<HoraExtra>> {
        return localDataSource.observeAll().map{entities ->
            entities.map{it.toDomain()}}
    }

    override suspend fun getHoraExtra(id: Int): HoraExtra?{
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        return localDataSource.exists(id)
    }

    override suspend fun upsert(horaExtra: HoraExtra): Int {
       localDataSource.upsert(horaExtra.toEntity())
        return horaExtra.horaExtraId ?: 0
    }
}