package edu.ucne.registrodeocupaciones.domain.ocupacion

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.UpsertOcupacionUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class UpsertOcupacionesTest {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: UpsertOcupacionUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = UpsertOcupacionUseCase(repository)
    }

    @Test
    fun `fails when descripcion is blank`() = runTest{
        coEvery { repository.getOcupacionesSync() } returns emptyList()
        val result = useCase(Ocupacion(descripcion = "", sueldo = 5.5))

        assertThat(result.isFailure, `is`(true))
        assertThat(result.exceptionOrNull(), `is`(instanceOf(IllegalArgumentException::class.java)))
    }

    @Test
    fun `fails when sueldo is zero or negative`() = runTest {
        coEvery { repository.getOcupacionesSync() } returns emptyList()
        val result = useCase(Ocupacion(descripcion = "Valida", sueldo = 0.0))

        assertThat(result.isFailure, `is`(true))
    }

    @Test
    fun `succeeds and returns id when repository upsert works`() = runTest {
        coEvery { repository.getOcupacionesSync() } returns emptyList()
        coEvery { repository.upsert(any()) } returns 7
        
        val result = useCase(Ocupacion(ocupacionId = 7, descripcion = "Valida", sueldo = 3.0))

        assertThat(result.isSuccess, `is`(true))
        assertThat(result.getOrNull(), `is`(equalTo(7)))
    }

}