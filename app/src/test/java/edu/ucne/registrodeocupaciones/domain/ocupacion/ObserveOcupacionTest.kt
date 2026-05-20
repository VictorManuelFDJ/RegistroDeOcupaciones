package edu.ucne.registrodeocupaciones.domain.ocupacion

import app.cash.turbine.test
import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import edu.ucne.registrodeocupaciones.domain.ocupacion.repository.OcupacionRepository
import edu.ucne.registrodeocupaciones.domain.ocupacion.useCase.ObserveOcupacionesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class ObserveOcupacionTest {
    private lateinit var repository: OcupacionRepository
    private lateinit var useCase: ObserveOcupacionesUseCase

    @Before
    fun setup(){
        repository = mockk()
        useCase = ObserveOcupacionesUseCase(repository)
    }

    @Test
    fun `emits lists from repository` () = runTest {
        val shared = MutableStateFlow<List<Ocupacion>>(emptyList())
        every { repository.observeOcupaciones() } returns shared

        useCase().test {
            assertThat(awaitItem(), `is`(emptyList()))

            val list1 = listOf(Ocupacion(1, "A", 1.0))
            shared.emit(list1)
            assertThat(awaitItem(), `is`(equalTo(list1)))

            val list2 = listOf(Ocupacion(2, "B", 2.0), Ocupacion(3, "C", 3.0))
            shared.emit(list2)
            assertThat(awaitItem(), `is`(equalTo(list2)))

            cancelAndIgnoreRemainingEvents()
        }
    }
}
