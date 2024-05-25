package net.octosystems.office.vakation.model.value

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class VacationDaysTest {
    @Test
    fun `GIVEN vacation days of 0 THEN an exception is thrown`() {
        assertFailure {
            VacationDays(0)
        }
    }

    @Test
    fun `GIVEN vacation days of 24 WHEN I ask for the value THEN 24 days is returned`() {
        assertThat(VacationDays(24))
            .prop(VacationDays::value)
            .isEqualTo(24)
    }
}
