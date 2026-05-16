package edu.ucne.registrodeocupaciones.data.empleado.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadosDao {

    @Upsert
    suspend fun upsert(entity: EmpleadosEntity): Long

    @Delete
    suspend fun delete(entity: EmpleadosEntity)

    @Query("Select * From RegistroEmpleados")
    fun observeAll(): Flow<List<EmpleadosEntity>>

    @Query("Select * From RegistroEmpleados where empleadoId = :id")
    suspend fun getById(id: Int): EmpleadosEntity?

    @Query("Delete From RegistroEmpleados Where empleadoId = :id")
    suspend fun deleteById(id: Int)

    @Query("Select exists(Select 1 From RegistroEmpleados Where empleadoId = :id )")
    suspend fun exists(id: Int): Boolean
}