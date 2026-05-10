package edu.ucne.registrodeocupaciones.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.ucne.registrodeocupaciones.data.local.OcupacionDao
import edu.ucne.registrodeocupaciones.data.local.OcupacionEntity

@Database(
    entities = [OcupacionEntity::class],
    version = 1
)
abstract  class OcupacionDB: RoomDatabase() {
    abstract fun OcupacionDao(): OcupacionDao
}