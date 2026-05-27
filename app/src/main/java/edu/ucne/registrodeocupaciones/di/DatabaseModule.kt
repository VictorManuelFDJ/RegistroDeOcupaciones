package edu.ucne.registrodeocupaciones.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrodeocupaciones.data.empleado.local.EmpleadosDao
import edu.ucne.registrodeocupaciones.data.horasExtra.local.HorasExtraDao
import edu.ucne.registrodeocupaciones.database.RegistroDb
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent:: class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideOcupacionDataBase(
        @ApplicationContext context: Context
    ): RegistroDb{
        return Room.databaseBuilder(
            context,
            RegistroDb::class.java,
            "Ocupacion_DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideOcupacionDao(database: RegistroDb): OcupacionDao{
        return database.OcupacionDao()
    }

    @Provides
    @Singleton
    fun provideEmpleadoDao(database: RegistroDb): EmpleadosDao{
        return database.EmpleadosDao()
    }

    @Provides
    @Singleton
    fun provideHorasExtraDao(database: RegistroDb): HorasExtraDao{
        return database.HorasExtraDao()
    }
}
