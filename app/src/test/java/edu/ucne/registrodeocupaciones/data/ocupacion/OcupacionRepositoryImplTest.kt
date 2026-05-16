package edu.ucne.registrodeocupaciones.data.ocupacion

import app.cash.turbine.test
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionDao
import edu.ucne.registrodeocupaciones.data.ocupacion.local.OcupacionEntity
import edu.ucne.registrodeocupaciones.data.ocupacion.repository.OcupacionRepositoryImpl
import edu.ucne.registrodeocupaciones.domain.ocupacion.model.Ocupacion
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class OcupacionRepositoryImplTest {

    private lateinit var dao: OcupacionDao
    private lateinit var repository: OcupacionRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = OcupacionRepositoryImpl(dao)
    }

    @Test
    fun `observeOcupaciones maps entities to domain`() = runTest {
        val shared = MutableSharedFlow<List<OcupacionEntity>>()
        every { dao.observeAll() } returns shared

        val job = launch {
            repository.observeOcupaciones().test {
                // First emission
                val entity1 = OcupacionEntity(ocupacionId = 1, descripcion = "A", sueldo = 5.0)
                val domain1 = Ocupacion(ocupacionId = 1, descripcion = "A", sueldo = 5.0)
                
                shared.emit(listOf(entity1))
                assertThat(awaitItem(), `is`(equalTo(listOf(domain1))))

                // Second emission (multiple items)
                val entity2 = OcupacionEntity(ocupacionId = 2, descripcion = "B", sueldo = 3.0)
                val entity3 = OcupacionEntity(ocupacionId = 3, descripcion = "C", sueldo = 4.0)
                val domain2 = Ocupacion(ocupacionId = 2, descripcion = "B", sueldo = 3.0)
                val domain3 = Ocupacion(ocupacionId = 3, descripcion = "C", sueldo = 4.0)
                
                shared.emit(listOf(entity2, entity3))
                assertThat(awaitItem(), `is`(equalTo(listOf(domain2, domain3))))

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun `getOcupacion returns mapped domain model when entity exists`() = runTest {
        val entity = OcupacionEntity(ocupacionId = 5, descripcion = "X", sueldo = 7.0)
        val domain = Ocupacion(ocupacionId = 5, descripcion = "X", sueldo = 7.0)
        coEvery { dao.getById(5) } returns entity

        val result = repository.getOcupacion(5)

        assertThat(result, `is`(equalTo(domain)))
    }

    @Test
    fun `getOcupacion returns null when entity missing`() = runTest {
        coEvery { dao.getById(42) } returns null

        val result = repository.getOcupacion(42)

        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun `upsert calls dao with mapped entity and returns id`() = runTest {
        coEvery { dao.upsert(any()) } returns 10L
        val ocupacion = Ocupacion(ocupacionId = 10, descripcion = "Nueva", sueldo = 1.0)
        val entity = OcupacionEntity(ocupacionId = 10, descripcion = "Nueva", sueldo = 1.0)

        val returnedId = repository.upsert(ocupacion)

        assertThat(returnedId, `is`(equalTo(10)))
        coVerify { dao.upsert(entity) }
    }

    @Test
    fun `delete calls dao deleteById`() = runTest {
        coEvery { dao.deleteById(12) } just runs

        repository.delete(12)

        coVerify { dao.deleteById(12) }
    }
}
