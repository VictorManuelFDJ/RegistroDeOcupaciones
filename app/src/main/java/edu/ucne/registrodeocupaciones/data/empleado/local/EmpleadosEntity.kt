package edu.ucne.registrodeocupaciones.data.empleado.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import edu.ucne.registrodeocupaciones.data.empleado.local.FrecuenciaDePago
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity
import java.time.LocalDate

@Entity(tableName = "registroEmpleados",
    foreignKeys = [
        ForeignKey(
            entity = OcupacionEntity::class,
            parentColumns = ["ocupacionId"],
            childColumns = ["ocupacionId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("ocupacionId")]
    )
data class EmpleadosEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0,
    val frecuenciaDePago: FrecuenciaDePago,
    val ocupacionId: Int = 0
)