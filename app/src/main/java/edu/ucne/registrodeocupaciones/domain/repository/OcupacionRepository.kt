package edu.ucne.registrodeocupaciones.domain.repository

import edu.ucne.registrodeocupaciones.data.local.OcupacionEntity
import edu.ucne.registrodeocupaciones.domain.model.Ocupacion
import kotlinx.coroutines.flow.Flow

interface OcupacionRepository {
    fun observeOcupaciones(): Flow<List<Ocupacion>>
    suspend fun getOcupacion(id: Int): Ocupacion?
    suspend fun upsert(ocupacion: Ocupacion): Int
    suspend fun delete(id: Int)
    suspend fun exists(id: Int): Boolean

}