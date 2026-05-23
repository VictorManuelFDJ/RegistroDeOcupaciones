package edu.ucne.registrodeocupaciones.domain.horasExtra.repository

import edu.ucne.registrodeocupaciones.domain.empleado.model.Empleado
import edu.ucne.registrodeocupaciones.domain.horasExtra.model.HoraExtra
import kotlinx.coroutines.flow.Flow

interface HoraExtraRepository {
    fun observeHoraExtra(): Flow<List<HoraExtra>>
    suspend fun getHoraExtra(id: Int): HoraExtra?
    suspend fun upsert(horaExtra: HoraExtra): Int
    suspend fun delete(id: Int)
    suspend fun exists(id: Int): Boolean
}
