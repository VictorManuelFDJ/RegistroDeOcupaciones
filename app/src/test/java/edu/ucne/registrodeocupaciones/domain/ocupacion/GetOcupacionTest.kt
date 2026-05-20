package edu.ucne.registrodeocupaciones.domain.ocupacion

import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.GetOcupacionUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class GetOcupacionTest  {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: GetOcupacionUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = GetOcupacionUseCase(repository)
    }

    @Test
    fun `returns task when repository finds it`() = runTest {
        val ocupacion = Ocupacion(1, "desc", 10.0)
        coEvery { repository.getOcupacion(1)} returns ocupacion
        val result = useCase(1)
        assertThat(result, `is`(equalTo(ocupacion)))
    }

    @Test
    fun `returns null when repository returns null`() = runTest{
        coEvery { repository.getOcupacion(999) } returns null
        val result = useCase(999)

        assertThat(result, `is`(nullValue()))
    }
}