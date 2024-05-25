package net.octosystems.office.vakation.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.Option
import io.mockk.every
import io.mockk.mockk
import net.octosystems.office.vakation.model.Country
import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.ValueSourceKey
import net.octosystems.office.vakation.repository.ConfigurationRepository
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.Locale
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class YamlValueSourceTest {
    private val repository = mockk<ConfigurationRepository>()
    private val valueSource = YamlValueSource(repository)
    private val config = VaKationConfiguration(country = Country(Locale.GERMANY.country))

    private val context = mockk<Context>()

    @ParameterizedTest(name = "Test Option: \"{1}\"")
    @MethodSource("testOptionsSource")
    fun `Test reading values from repositories`(option: Option, sourceKey: String, expectedValue: List<String>) {
        every { option.valueSourceKey } returns sourceKey
        every { repository.read() } returns config

        val result = valueSource.getValues(context, option).map { it.values }

        assertThat(result).isEqualTo(expectedValue)
    }


    @Suppress("UnusedPrivateMember")
    private fun <T : Any> testOptionsSource(): Stream<Arguments> {
        val arguments = mutableListOf<Arguments>()

        ValueSourceKey.ArgumentSourceKey.entries.forEach { key ->
            arguments.add(Arguments.of(mockk<Option>(), key.value, listOf(config.getValueFromSourceKey(key))))
        }

        ValueSourceKey.OptionSourceKey.entries.forEach { key ->
            key.value.forEach { value ->
                arguments.add(Arguments.of(mockk<Option>(), value, listOf(config.getValueFromSourceKey(key))))
            }
        }
        return arguments.stream()
    }

}
