package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.application.TestData
import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketCategory
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.TicketRepositoryImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TicketRepositoryTest {
    private val ticketRepository = TicketRepositoryImpl()
    private lateinit var testData: TestData

    @BeforeEach
    fun initTest() {
        testData = TestData()
    }

    @Test
    fun `GIVEN noTickets WHEN getAllTickets THEN returnEmptyList`() {
        //GIVEN
        //No Tickets

        //WHEN
        val tickets = ticketRepository.getAllTickets()

        //THEN
        assertEquals(listOf(), tickets)
    }

    @Test
    fun `GIVEN threeTickets WHEN getAllTickets THEN returnListWithThreeTickets`() {
        //GIVEN
        val em = PersistenceManager.getEntityManagerInstance()

        val ticket1 =
            Ticket(ticketNumber = 22, ticketCategory = testData.szeneDay1TicketCategory1, order = DefaultData.order)
        val ticket2 =
            Ticket(ticketNumber = 32, ticketCategory = testData.szeneDay1TicketCategory1, order = DefaultData.order)
        val ticket3 =
            Ticket(ticketNumber = 21, ticketCategory = testData.szeneDay1TicketCategory1, order = DefaultData.order)
        val givenTicketList = listOf(ticket1, ticket2, ticket3)


        em.transaction.begin()
        givenTicketList.forEach { em.persist(it) }
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        val tickets = ticketRepository.getAllTickets()

        //THEN
        assertEquals(givenTicketList, tickets)
    }


    @Test
    fun `GIVEN listOfTickets WHEN getTicketsByCategory THEN returnListOfTicketsByCategory`() {
        // GIVEN
        val tickets = listOf(
            Ticket(ticketNumber = 1, id = UUID.randomUUID(), ticketCategory = testData.szeneDay1TicketCategory1),
            Ticket(ticketNumber = 2, id = UUID.randomUUID(), ticketCategory = testData.szeneDay2TicketCategory1),
            Ticket(
                ticketNumber = 3,
                id = UUID.randomUUID(),
                ticketCategory = testData.kulturhausLectureTicketcategory1
            ),
            Ticket(
                ticketNumber = 4,
                id = UUID.randomUUID(),
                ticketCategory = testData.kulturhausLectureTicketcategory2
            ),
            Ticket(ticketNumber = 5, id = UUID.randomUUID(), ticketCategory = testData.szeneDay2TicketCategory1)
        )
        val expectedTickets = tickets.filter { it.ticketCategory == testData.szeneDay2TicketCategory1 }

        val em = PersistenceManager.getEntityManagerInstance()
        em.transaction.begin()
        tickets.forEach { em.persist(it) }
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        // WHEN
        val actualTickets = ticketRepository.getTicketsByCategory(
            ticketCategoryId = testData.szeneDay2TicketCategory1.id
        )

        // THEN
        assertEquals(expectedTickets, actualTickets)
    }

    @Test
    fun `GIVEN TicketCategory, amount, ticketNumbers, availableTicketNumbers WHEN createTicketsForCategory THEN returnListOfTickets`() {

        //GIVEN

        val ticketCategory =
            TicketCategory(id = UUID.randomUUID(), name = "Szene Day 2", price = 16.99, totalTickets = 10)
        val ticketNumbers = listOf(1, 2, 3, 4, 5)
        val availableTicketNumbers = listOf(1, 2, 4, 5)

        //WHEN

        val createdTickets =
            ticketRepository.createTicketsForCategory(
                ticketCategory,
                ticketNumbers.size,
                ticketNumbers,
                availableTicketNumbers
            )

        //THEN

        assertEquals(ticketNumbers.size, createdTickets.size)
        assertTrue(createdTickets.all { it.ticketCategory == ticketCategory })
        assertTrue(createdTickets.map { it.ticketNumber }.containsAll(ticketNumbers))
        assertTrue(createdTickets.map { it.ticketNumber }.containsAll(availableTicketNumbers))

    }

    @Test
    fun `GIVEN TicketCategory AND amount AND availableTicketNumbers WHEN createTicketsForCategory THEN returnListOfTickets`() {

        //GIVEN

        val ticketCategory =
            TicketCategory(id = UUID.randomUUID(), name = "Szene Day 2", price = 16.99, totalTickets = 10)
        val amount = 5
        val ticketNumbers = listOf<Int>()
        val availableTicketNumbers = listOf(2, 3, 4, 5, 6)

        //WHEN

        val createdTickets =
            ticketRepository.createTicketsForCategory(ticketCategory, amount, ticketNumbers, availableTicketNumbers)

        //THEN

        assertEquals(amount, createdTickets.size)
        assertEquals(createdTickets.map { it.ticketNumber }, availableTicketNumbers)
    }

    @Test
    fun `GIVEN existing tickets in the repository and valid ticket IDs WHEN removeTicketsFromRepository THEN remove tickets with specified IDs`() {
        // GIVEN
        val em = PersistenceManager.getEntityManagerInstance()
        val ticket1 =
            Ticket(id = UUID.randomUUID(), ticketNumber = 1, ticketCategory = testData.szeneDay1TicketCategory1)
        val ticket2 =
            Ticket(id = UUID.randomUUID(), ticketNumber = 2, ticketCategory = testData.szeneDay1TicketCategory1)
        val ticket3 =
            Ticket(id = UUID.randomUUID(), ticketNumber = 3, ticketCategory = testData.szeneDay1TicketCategory1)
        em.transaction.begin()
        em.persist(ticket1)
        em.persist(ticket2)
        em.persist(ticket3)
        em.transaction.commit()
        em.clear()
        val ticketIdsToRemove = listOf(ticket1.id, ticket3.id)
        val expected = listOf(ticket2)
        // WHEN
        ticketRepository.removeTicketsFromRepository(ticketIdsToRemove)

        // THEN
        em.clear()
        val remainingTickets = em.createQuery("SELECT t FROM Ticket t", Ticket::class.java).resultList.toList()
        assertEquals(expected, remainingTickets)
    }

    @Test
    fun `GIVEN ticket WHEN getTicketById THEN returnTicket`() {
        //GIVEN
        val em = PersistenceManager.getEntityManagerInstance()

        val ticketExpected =
            Ticket(ticketNumber = 22, ticketCategory = testData.szeneDay1TicketCategory1, order = DefaultData.order)



        em.transaction.begin()
        em.persist(ticketExpected)
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        val ticketActual = ticketRepository.getTicketById(ticketExpected.id)

        //THEN
        assertEquals(ticketExpected, ticketActual)
    }

    @Test
    fun `GIVEN noTicket WHEN getTicketById THEN returnDefaultTicket`() {
        //GIVEN
        val ticketExpected = DefaultData.ticket

        //WHEN
        val ticketActual = ticketRepository.getTicketById(UUID.randomUUID())

        //THEN
        assertEquals(ticketExpected, ticketActual)
    }
}