package edu.ucne.registrodeocupaciones.data.repository


import edu.ucne.registrodeocupaciones.data.local.OcupacionDao
import edu.ucne.registrodeocupaciones.data.mapper.toDomain
import edu.ucne.registrodeocupaciones.data.mapper.toEntity
import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OcupacionRepositoryImpl @Inject constructor(
    private val localDataSource: OcupacionDao
): OcupacionRepository{
    override fun observeOcupaciones(): Flow<List<Ocupacion>> {
        return localDataSource.observeAll().map{entities ->
            entities.map { it.toDomain()}}
    }

    override suspend fun getOcupacion(id: Int): Ocupacion? {
        return localDataSource.getById(id)?.toDomain()
    }

    override suspend fun upsert(ocupacion: Ocupacion): Int {
        return localDataSource.upsert(ocupacion.toEntity()).toInt()
    }

    override suspend fun delete(id: Int) {
        localDataSource.deleteById(id)
    }

    override suspend fun exists(id: Int): Boolean {
        return localDataSource.exists(id)
    }

}