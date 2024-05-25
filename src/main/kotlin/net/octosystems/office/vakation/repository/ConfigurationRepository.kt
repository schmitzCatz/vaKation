package net.octosystems.office.vakation.repository

import net.octosystems.office.vakation.model.VaKationConfiguration

interface ConfigurationRepository {
    fun read(): VaKationConfiguration

    fun save(configuration: VaKationConfiguration)
}
