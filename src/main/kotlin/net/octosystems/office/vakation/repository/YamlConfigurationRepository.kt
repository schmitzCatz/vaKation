package net.octosystems.office.vakation.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.octosystems.office.vakation.model.Country
import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.value.CountryCode
import net.octosystems.office.vakation.model.value.StateCode
import net.octosystems.office.vakation.model.value.VacationDays
import net.octosystems.office.vakation.repository.entity.Configuration
import java.nio.file.Path
import java.time.Year

class YamlConfigurationRepository(
    private val path: Path,
    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
) : ConfigurationRepository {

    private var configuration: VaKationConfiguration? = null


    override fun read(): VaKationConfiguration {

        if (configuration == null) {
            val entity = objectMapper.readValue(path.toFile(), Configuration::class.java)
            configuration = VaKationConfiguration(
                Year.now(),
                entity.workdays,
                entity.extraDays,
                VacationDays(entity.vacationDays),
                Country(CountryCode(entity.country.country), entity.country.state?.let { StateCode(it) }),
            )
        }

        return configuration as VaKationConfiguration
    }

    override fun save(configuration: VaKationConfiguration) =
        objectMapper.writer().writeValue(path.toFile(), configuration)
}
