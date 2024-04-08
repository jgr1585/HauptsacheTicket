package at.fhv.teamh.hauptsach_ticket.backend.application

import kotlin.test.assertEquals
import kotlin.test.assertTrue


object AssertExtension {
    fun assertContainsAll(expected: List<*>, actual: List<*>) {
        assertEquals(expected.size, actual.size)

        assertTrue(expected.containsAll(actual), "Expected: $expected, Actual: $actual")
        assertTrue(actual.containsAll(expected), "Expected: $expected, Actual: $actual")
    }
}