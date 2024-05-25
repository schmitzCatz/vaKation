package net.octosystems.office.vakation.model.value

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.jupiter.api.Test

class Iso3166Test {
    @Test
    fun `GIVEN invalid country code of abcd THEN an exception is thrown`() {
        assertFailure { StateCode("abcd") }
    }

    @Test
    fun `GIVEN invalid country code of a THEN an exception is thrown`() {
        assertFailure { StateCode("a") }
    }

    @Test
    fun `GIVEN valid country code of NW WHEN I request THEN I get NW`() {
        assertThat(StateCode("NW"))
            .prop(StateCode::value)
            .isEqualTo("NW")
    }
}
