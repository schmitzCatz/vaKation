package net.octosystems.office.vakation.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource


class ValueSourceKeyTest {

    @ParameterizedTest
    @EnumSource(ValueSourceKey.ArgumentSourceKey::class)
    fun `Validate argument lookup`(key: ValueSourceKey.ArgumentSourceKey){
        val result = ValueSourceKey.lookUpSourceKey(key.value)

        assertThat(result).isEqualTo(key)
    }

    @ParameterizedTest
    @EnumSource(ValueSourceKey.OptionSourceKey::class)
    fun `Validate option lookup`(key: ValueSourceKey.OptionSourceKey) {

        key.value.forEach {
            val result = ValueSourceKey.lookUpSourceKey(it)
            assertThat(result).isEqualTo(key)
        }

    }

}
