package edu.ucne.registrodeocupaciones.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registrodeocupaciones.data.empleado.local.Converters
import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosDao
import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosEntity
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionDao
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity

@Database(
    entities = [OcupacionEntity::class, EmpleadosEntity:: class],
    version = 2
)
@TypeConverters(Converters::class)
abstract  class RegistroDb: RoomDatabase() {
    abstract fun OcupacionDao(): OcupacionDao
    abstract fun EmpleadosDao(): EmpleadosDao
}