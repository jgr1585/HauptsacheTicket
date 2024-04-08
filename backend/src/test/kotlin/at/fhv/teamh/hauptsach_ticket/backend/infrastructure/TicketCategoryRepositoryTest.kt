package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.application.AssertExtension
import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.TestData
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.TicketCategoryRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class TicketCategoryRepositoryTest {
    private val ticketCategoryRepository = TicketCategoryRepositoryImpl()
    private lateinit var testData: TestData

    @BeforeEach
    fun initTest() {
        testData = TestData()
    }

    @Test
    fun `GIVEN eventID WHEN getTicketCategoriesForEvent THEN returnTicketList`() {
        //GIVEN
        val event = testData.events.first()
        val ticketCategory = testData.ticketCategories.filter { it.event == event }

        //WHEN
        val actual = ticketCategoryRepository.getTicketCategoriesForEvent(event.id)
        val actual2 =
            ticketCategoryRepository.getTicketCategoriesForEvent(UUID.fromString("00000000-0000-0000-0000-000000000000"))

        //THEN
        AssertExtension.assertContainsAll(ticketCategory, actual)
        assertEquals(emptyList(), actual2)
    }

    @Test
    fun `GIVEN ticketCategory WHEN getTicketCategoryById THEN returnCategory`() {
        //GIVEN
        val ticketCategoryExpected = testData.ticketCategories.first()

        //WHEN
        val ticketCategoryActual = ticketCategoryRepository.getTicketCategoryById(ticketCategoryExpected.id)

        //THEN
        assertEquals(ticketCategoryExpected, ticketCategoryActual)
    }

    @Test
    fun `GIVEN noCategory WHEN getTicketCategoryById THEN returnDefaultCategory`() {
        //GIVEN
        val ticketCategoryId = UUID.randomUUID()
        val ticketCategoryExpected = DefaultData.ticketCategory

        //WHEN
        val ticketCategoryActual = ticketCategoryRepository.getTicketCategoryById(ticketCategoryId)

        //THEN
        assertEquals(ticketCategoryExpected, ticketCategoryActual)
    }

    @Test
    fun `GIVEN category WHEN getTicketNumbers THEN returnListOfTicketNumbers`() {
        //GIVEN
        val ticketCategory = testData.ticketCategories.first()
        val ticketNumbersExpected = testData.ticketNumbers.filter { it.firstOrNull()?.ticketCategory == ticketCategory }.flatten()

        //WHEN
        val ticketNumbersActual = ticketCategoryRepository.getTicketNumbers(ticketCategory.id)

        //THEN
        assertEquals(ticketNumbersExpected, ticketNumbersActual)
    }

    @Test
    fun `GIVEN CategoryAndNumber WHEN markTicketAsUnavailable THEN returnUnavailableNumber`() {
        //GIVEN
        val ticketCategory = testData.ticketCategories.first()
        val ticketNumberExpected = testData.ticketNumbers.first { it.firstOrNull()?.ticketCategory == ticketCategory }.first()
        ticketNumberExpected.available = false

        //WHEN
        ticketCategoryRepository.markTicketAsUnavailable(ticketCategory.id, ticketNumberExpected.number)
        val ticketNumberActual = ticketCategoryRepository.getTicketNumbers(ticketCategory.id).first { !it.available }

        //THEN
        assertEquals(ticketNumberExpected, ticketNumberActual)
    }

    @Test
    fun `GIVEN unavailableTicketNumberInCategory WHEN makeTicketNumbersAvailable THEN returnAvailableTicketNumber`() {
        //GIVEN
        val ticketCategory = testData.ticketCategories.first()
        val ticketNumberExpected = testData.ticketNumbers.first { it.firstOrNull()?.ticketCategory == ticketCategory }.first()

        //WHEN
        ticketCategoryRepository.markTicketAsUnavailable(ticketCategory.id, ticketNumberExpected.number)
        ticketCategoryRepository.makeTicketNumbersAvailable(ticketCategory.id, listOf(ticketNumberExpected.number))

        val ticketNumberActual = ticketCategoryRepository.getTicketNumbers(ticketCategory.id)
            .first { it.number == ticketNumberExpected.number }

        //THEN
        assertEquals(ticketNumberExpected, ticketNumberActual)
    }
}