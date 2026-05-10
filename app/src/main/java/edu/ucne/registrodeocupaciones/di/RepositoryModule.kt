package edu.ucne.registrodeocupaciones.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrodeocupaciones.data.repository.OcupacionRepositoryImpl
import edu.ucne.registrodeocupaciones.domain.repository.OcupacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindOcupacionRepository(
        impl: OcupacionRepositoryImpl
    ): OcupacionRepository
}