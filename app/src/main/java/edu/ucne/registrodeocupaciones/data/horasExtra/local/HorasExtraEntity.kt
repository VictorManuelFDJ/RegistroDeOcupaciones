package edu.ucne.registrodeocupaciones.data.horasExtra.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "horasExtras")
data class HorasExtraEntity(
    @PrimaryKey(autoGenerate = true)
    val horaExtraId: Int = 0,
    val empleadoId: Int = 0,
    val fecha: LocalDate = LocalDate.now(),
    val cantidadHoras: Int = 0,
    val tipoHoraExtra: TipoHoraExtra,
    val recargo: Double = 0.0,
    val esPuestoDireccion: Boolean = false
)
