package edu.ucne.registrodeocupaciones.domain.ocupacion

import edu.ucne.registrodeocupaciones.domain.empleado.repository.EmpleadoRepository
import edu.ucne.registrodeocupaciones.domain.empleado.useCase.DeleteEmpleadoUseCase
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.DeleteOcupacionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteOcuapacionTest {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: DeleteOcupacionUseCase

    @Before
    fun septp(){
        repository = mockk(relaxed = true)
        useCase = DeleteOcupacionUseCase(repository)
    }
    @Test
    fun `calls repository delete with id`() = runTest {
        coEvery { repository.delete(5) } just runs

        useCase(5)

        coVerify { repository.delete(5) }
    }
}