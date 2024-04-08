package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.application.AssertExtension.assertContainsAll
import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.TestData
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.EventRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class EventRepositoryTest {
    private val eventRepository = EventRepositoryImpl()
    private lateinit var testDate: TestData

    @BeforeEach
    fun initTest() {
        testDate = TestData()
    }

    @Test
    fun `GIVEN events WHEN getEvent THEN returnEvent`() {
        //GIVEN
        val event = testDate.events

        //WHEN
        val actual = eventRepository.getEvents(eventsPerPage = event.size)
        val actual2 = eventRepository.getEvents(event.size, 1)

        //THEN
        assertEquals(event, actual)
        assertEquals(emptyList(), actual2)

        assertThrows<IllegalArgumentException> {
            eventRepository.getEvents(-1, 1)
        }
    }

    @Test
    fun `GIVEN events WHEN totalEvent THEN returnEvent`() {
        //GIVEN
        val event = testDate.events
        val expected = event.count()

        //WHEN
        val actual = eventRepository.totalEvents()

        //THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN events WHEN searchEvent THEN returnEvent`() {
        //GIVEN
        val event = testDate.events
        val expected = event.filter { it.series.name.contains("szene", ignoreCase = true) }
        val expected1 = event.filter { it.series.name.contains("bregenzer Festspiele", ignoreCase = true) }
        val expectedArtist = event.filter { it.series.artist.contains("Thomas Feilhauer") }
        val expectedArtist2 = event.filter { it.series.artist.contains("Thomas") }
        val expectedArtist3 = event.filter { it.series.artist.contains("Various") }
        val expectedArtist4 = event.filter { it.series.artist.contains("") }
        val expectedArtist5 = event.filter { it.series.artist.contains("Test") }
        val expectedDate = event.filter { it.date >= LocalDate.of(2023, 3, 27) }
        val expectedDate2 = event.filter { it.date >= LocalDate.of(2022, 3, 27) }
        val expectedDate3 = event.filter { it.date >= LocalDate.of(2024, 3, 27) }
        val expectedDate4 = event.filter { it.date >= LocalDate.of(2023, 6, 27) }
        //WHEN
        val actual = eventRepository.searchEvents("szene")
        val actual1 = eventRepository.searchEvents("bregenzer Festspiele")
        val actualArtist = eventRepository.searchEvents("Thomas Feilhauer")
        val actualArtist2 = eventRepository.searchEvents("Thomas")
        val actualArtist3 = eventRepository.searchEvents("Various")
        val actualArtist4 = eventRepository.searchEvents("", eventsPerPage = event.size)
        val actualArtist5 = eventRepository.searchEvents("Test")
        val actualDate = eventRepository.searchEvents("", date = LocalDate.of(2023, 3, 27), eventsPerPage = event.size)
        val actualDate2 = eventRepository.searchEvents("", date = LocalDate.of(2022, 3, 27), eventsPerPage = event.size)
        val actualDate3 = eventRepository.searchEvents("", date = LocalDate.of(2024, 3, 27), eventsPerPage = event.size)
        val actualDate4 = eventRepository.searchEvents("", date = LocalDate.of(2023, 6, 27), eventsPerPage = event.size)
        //THEN
        assertContainsAll(expected, actual)
        assertContainsAll(expected1, actual1)
        assertContainsAll(expectedArtist, actualArtist)
        assertContainsAll(expectedArtist2, actualArtist2)
        assertContainsAll(expectedArtist3, actualArtist3)
        assertContainsAll(expectedArtist4, actualArtist4)
        assertContainsAll(expectedArtist5, actualArtist5)
        assertContainsAll(expectedDate, actualDate)
        assertContainsAll(expectedDate2, actualDate2)
        assertContainsAll(expectedDate3, actualDate3)
        assertContainsAll(expectedDate4, actualDate4)

    }

    @Test
    fun `GIVEN events WHEN totalSearchEvent THEN returnEvent`() {
        //GIVEN
        val event = testDate.events
        val expected = event.count { it.series.name.contains("szene", ignoreCase = true) }
        val expected2 = event.count { it.series.name.contains("bregenzer Festspiele", ignoreCase = true) }
        val expectedArtist = event.count { it.series.artist.contains("Thomas Feilhauer") }
        val expectedArtist2 = event.count { it.series.artist.contains("Thomas") }
        val expectedArtist3 = event.count { it.series.artist.contains("Various") }
        val expectedArtist4 = event.count { it.series.artist.contains("") }
        val expectedArtist5 = event.count { it.series.artist.contains("Test") }
        val expectedDate = event.count { it.date >= LocalDate.of(2023, 3, 27) }
        val expectedDate2 = event.count { it.date >= LocalDate.of(2022, 3, 27) }
        val expectedDate3 = event.count { it.date >= LocalDate.of(2024, 3, 27) }
        val expectedDate4 = event.count { it.date >= LocalDate.of(2023, 6, 27) }

        //WHEN
        val actual = eventRepository.totalSearchEvents("szene")
        val actual2 = eventRepository.totalSearchEvents("bregenzer Festspiele")
        val actualArtist = eventRepository.totalSearchEvents("Thomas Feilhauer")
        val actualArtist2 = eventRepository.totalSearchEvents("Thomas")
        val actualArtist3 = eventRepository.totalSearchEvents("Various")
        val actualArtist4 = eventRepository.totalSearchEvents("")
        val actualArtist5 = eventRepository.totalSearchEvents("Test")
        val actualDate = eventRepository.totalSearchEvents("", date = LocalDate.of(2023, 3, 27))
        val actualDate2 = eventRepository.totalSearchEvents("", date = LocalDate.of(2022, 3, 27))
        val actualDate3 = eventRepository.totalSearchEvents("", date = LocalDate.of(2024, 3, 27))
        val actualDate4 = eventRepository.totalSearchEvents("", date = LocalDate.of(2023, 6, 27))
        //THEN

        assertEquals(expected, actual)
        assertEquals(expected2, actual2)
        assertEquals(expectedArtist, actualArtist)
        assertEquals(expectedArtist2, actualArtist2)
        assertEquals(expectedArtist3, actualArtist3)
        assertEquals(expectedArtist4, actualArtist4)
        assertEquals(expectedArtist5, actualArtist5)
        assertEquals(expectedDate, actualDate)
        assertEquals(expectedDate2, actualDate2)
        assertEquals(expectedDate3, actualDate3)
        assertEquals(expectedDate4, actualDate4)
    }

    @Test
    fun `GIVEN TicketCategory WHEN getEventByCategoryId THEN returnEvent`() {
        //GIVEN
        val eventExpected = testDate.ticketCategories.first().event
        val ticketCategory = testDate.ticketCategories.first()

        //WHEN
        val eventActual = eventRepository.getEventByCategoryId(ticketCategory.id)

        //THEN
        assertEquals(eventExpected, eventActual)
    }

    @Test
    fun `GIVEN noCategory WHEN getEventByCategoryId THEN returnDefaultEvent`() {
        //GIVEN
        val eventExpected = DefaultData.event
        val categoryId = UUID.randomUUID()

        //WHEN
        val eventActual = eventRepository.getEventByCategoryId(categoryId)

        //THEN
        assertEquals(eventExpected, eventActual)
    }

    @Test
    fun `GIVEN events WHEN findEventName THEN returnEvent`() {
        //GIVEN
        val event = testDate.events
        val expected = event.filter { it.name.contains("") }
        val expected2 = event.filter { it.name.contains("Vortrag im Kulturhaus") }
        val expected3 = event.filter { it.name.contains("Vortrag") }
        val expected4 = event.filter { it.name.contains("Frühlings Messe") }

        // WHEN
        val actual = eventRepository.findEventName("")
        val actual2 = eventRepository.findEventName("Vortrag im Kulturhaus")
        val actual3 = eventRepository.findEventName("Vortrag")
        val actual4 = eventRepository.findEventName("Frühlings Messe")
        //THEN
        assertEquals(expected,actual)
        assertEquals(expected2,actual2)
        assertEquals(expected3,actual3)
        assertEquals(expected4,actual4)
    }

    @Test
    fun `GIVEN events WHEN findEventGenre THEN returnEvent`() {
        //GIVEN
        val event = testDate.events
        val expected = event.filter { it.genre.contains("") }
        val expected2 = event.filter { it.genre.contains("Vortrag") }
        val expected3 = event.filter { it.genre.contains("Fes") }
        val expected4 = event.filter { it.genre.contains("Coaching") }

        //WHEN
        val actual = eventRepository.findEventGenre("")
        val actual2 = eventRepository.findEventGenre("Vortrag")
        val actual3 = eventRepository.findEventGenre("Fes")
        val actual4 = eventRepository.findEventGenre("Coaching")
        //THEN
        assertEquals(expected,actual)
        assertEquals(expected2,actual2)
        assertEquals(expected3,actual3)
        assertEquals(expected4,actual4)
    }


}