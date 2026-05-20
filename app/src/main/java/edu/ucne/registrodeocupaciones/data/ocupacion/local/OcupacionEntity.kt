package edu.ucne.registrodeocupaciones.data.ocupacion.local

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionId: Int = 0,
    val descripcion: String,
    val sueldo: Double
)