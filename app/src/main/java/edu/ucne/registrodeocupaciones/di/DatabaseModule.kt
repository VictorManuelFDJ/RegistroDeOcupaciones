package edu.ucne.registrodeocupaciones.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrodeocupaciones.data.database.OcupacionDB
import edu.ucne.registrodeocupaciones.data.local.OcupacionDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent:: class)
object DatabaseModule{
    @Provides
    @Singleton
    fun provideOcupacionDataBase(
        @ApplicationContext context: Context
    ): OcupacionDB{
        return Room.databaseBuilder(
            context,
            OcupacionDB::class.java,
            "Ocupacion_DB"
        ).build()
    }
    @Provides
    @Singleton
    fun provideOcupacionDao(database: OcupacionDB): OcupacionDao{
        return database.OcupacionDao()
    }
}
