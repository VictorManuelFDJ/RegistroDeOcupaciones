package edu.ucne.registrodeocupaciones.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {

    @Upsert
    suspend fun upsert(entity: OcupacionEntity)

    @Query("Select * FROM ocupaciones")
    fun observall():Flow<List<OcupacionEntity>>

    @Query("Select exists(Select 1 From ocupaciones WHERE descripcion = :descripcion )")
    suspend fun existeDescripcion(descripcion : String) : Boolean
}