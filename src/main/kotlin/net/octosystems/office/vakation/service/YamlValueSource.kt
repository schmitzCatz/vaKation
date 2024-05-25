package net.octosystems.office.vakation.service

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.Option
import com.github.ajalt.clikt.sources.ValueSource
import net.octosystems.office.vakation.model.ValueSourceKey
import net.octosystems.office.vakation.model.ValueSourceKey.ArgumentSourceKey.VACATION_DAYS
import net.octosystems.office.vakation.model.ValueSourceKey.ArgumentSourceKey.YEAR
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey.COUNTRY
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey.EXTRA_DAYS
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey.FORMAT
import net.octosystems.office.vakation.model.ValueSourceKey.OptionSourceKey.WORKDAY
import net.octosystems.office.vakation.repository.ConfigurationRepository
import net.octosystems.office.vakation.repository.YamlConfigurationRepository
import java.nio.file.Path
import java.time.format.DateTimeFormatter

class YamlValueSource(
    private val repository: ConfigurationRepository = YamlConfigurationRepository(Path.of("."))
) : ValueSource {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun getValues(context: Context, option: Option): List<ValueSource.Invocation> {
        val key = option.valueSourceKey ?: return emptyList()

        val config = repository.read()

        val result = when (val sourceKey = ValueSourceKey.lookUpSourceKey(key)) {
            YEAR -> ValueSource.Invocation.value(config.year.value)
            VACATION_DAYS -> ValueSource.Invocation.value(config.vacationDays.value)
            WORKDAY -> ValueSource.Invocation(config.workdays.map { it.name })
            EXTRA_DAYS -> ValueSource.Invocation(config.extraDays.map { it.format(formatter) })
            COUNTRY -> ValueSource.Invocation.value(config.country)
            FORMAT -> ValueSource.Invocation.value(config.format.name)
            else -> throw IllegalArgumentException("Unknown source key: $sourceKey")
        }

        return listOf(result)
    }

}
