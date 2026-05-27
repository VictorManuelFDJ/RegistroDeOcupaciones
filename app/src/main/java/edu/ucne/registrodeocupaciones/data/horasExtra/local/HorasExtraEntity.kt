package edu.ucne.registrodeocupaciones.data.horasExtra.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosEntity
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity
import java.time.LocalDate

@Entity(tableName = "horasExtras",
    foreignKeys = [
        ForeignKey(
            entity = EmpleadosEntity::class,
            parentColumns = ["empleadoId"],
            childColumns = ["empleadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("empleadoId")]
    )

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
