package edu.ucne.registrodeocupaciones.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrodeocupaciones.data.empleado.repository.EmpleadoRepositoryImpl
import edu.ucne.registrodeocupaciones.data.ocupacion.repository.OcupacionRepositoryImpl
import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindOcupacionRepository(
        impl: OcupacionRepositoryImpl
    ): OcupacionRepository

    @Binds
    @Singleton
    abstract fun bindEmpleadoRepository(
        impl: EmpleadoRepositoryImpl
    ): EmpleadoRepository
}