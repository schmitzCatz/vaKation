package net.octosystems.office.vakation.repository

import net.octosystems.office.vakation.model.VaKationConfiguration
import net.octosystems.office.vakation.model.value.VacationDays

private const val DEFAULT_VACATION_DAYS = 24

class MemoryConfigurationRepository: ConfigurationRepository {

    private var config = VaKationConfiguration(vacationDays = VacationDays(DEFAULT_VACATION_DAYS))

    override fun read(): VaKationConfiguration = config

    override fun save(configuration: VaKationConfiguration) {
        config = configuration
    }
}
