package edu.ucne.registrodeocupaciones.data.horasExtra.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import kotlinx.coroutines.flow.Flow

@Dao
interface HorasExtraDao {
    @Upsert
    suspend fun upsert(entity: HorasExtraEntity)

    @Delete
    suspend fun delete(entity: HorasExtraEntity)

    @Query("Select * FROM horasExtras")
    fun observeAll(): Flow<List<HorasExtraEntity>>

    @Query("Select * From horasExtras Where horaExtraId = :id")
    suspend fun getById(id: Int): HorasExtraEntity?

    @Query("Delete From horasExtras Where horaExtraId = :id")
    suspend fun deleteById(id: Int)

    @Query("Select exists(Select 1 From horasExtras WHERE horaExtraId = :id )")
    suspend fun exists(id: Int): Boolean
}