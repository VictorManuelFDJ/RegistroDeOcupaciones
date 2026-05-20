package edu.ucne.registrodeocupaciones.data.empleado.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "RegistroEmpleados")
data class EmpleadosEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombre: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0,
    val ocupacionId: Int = 0
)